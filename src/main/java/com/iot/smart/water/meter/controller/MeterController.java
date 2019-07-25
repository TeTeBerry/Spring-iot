package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.MemberMapper;
import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.dao.UserMapper;
import com.iot.smart.water.meter.dao.VolumeMapper;
import com.iot.smart.water.meter.model.*;
import com.iot.smart.water.meter.response.ErrorCode;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.service.MeterService;
import com.iot.smart.water.meter.util.RoleManager;
import com.iot.smart.water.meter.util.TokenUtil;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iot/meter")
public class MeterController {

    @Autowired
    private MeterService meterService;

    @Autowired
    private MeterMapper meterMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private VolumeMapper volumeMapper;

    @Autowired
    private RoleManager roleManager;

    @GetMapping("/getWaterBill")
	@CrossOrigin(origins="*")
    public Response getWaterBill(@RequestParam("meterName") String meterName,
                                 @RequestParam("password") String password) {
        Response response = new Response();
        Meter meter  = meterMapper.selectMeterByName(meterName);
        Member member = memberMapper.selectMemberById(meter.getMember_id());
        if (!member.getPassword().equals(password)) {
            response.setCode(ErrorCode.INVALID_PASSWORD);
            response.setMsg("invalid password");
            return  response;
        }
        response.setData(meterService.getWaterBill(member,meterName));
        response.setMsg("get water bill success");
        return response;
    }

	@PostMapping("/setVolume")
	@CrossOrigin(origins="*")
    public Response setVolume(@RequestHeader("token") String token,
                              @RequestParam("member_id") Integer member_id,
                              @RequestParam("meter_id") Integer meter_id,
                              @RequestParam("volume") long newVolumeNum,
                              @RequestParam("password") String password) {
        Response response = new Response();
        Integer id = TokenUtil.getId(token);
        if (id == null) {
            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");
            return response;
        }
        User user = userMapper.selectUserById(id);
        if (user == null) {
            TokenUtil.clear(id, token);
            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");
            return response;
        }
        if (!roleManager.isMember(user.getId())) {
            response.setCode(ErrorCode.PERMISSION_ERROR);
            response.setMsg("permission error");
            return response;
        }

          Member member = memberMapper.selectMemberById(member_id);

        if (!member.getPassword().equals(password)) {
            response.setCode(ErrorCode.EMPTY_PASSWORD);
            response.setMsg("invalid password");
            return response;
        }

        if (newVolumeNum <= 0) {
            response.setCode(ErrorCode.INVALID_PARAMS);
            response.setMsg("invalid volume");
            return response;
        }
        if (memberMapper.selectMemberById(member_id) == null) {
            response.setCode(ErrorCode.INVALID_PARAMS);
            response.setMsg("invalid member id");
            return response;
        }
        if (meterMapper.selectMeterById(meter_id) == null) {
            response.setCode(ErrorCode.INVALID_PARAMS);
            response.setMsg("invalid meter id");
            return response;
        }
        Volume volumeBean = volumeMapper.selectVolumeById(member_id, meter_id);
        if (volumeBean == null) {
            volumeBean = new Volume();
            volumeBean.setMember_id(member_id);
            volumeBean.setMeter_id(meter_id);
        }
        if (!meterService.setMemberVolume(member,volumeBean, newVolumeNum)) {
            response.setCode(ErrorCode.INVALID_SET_VOLUME_LIMIT);
            response.setMsg("set volume limit");
            return response;
        }
        response.setMsg("update member volume success");
        return response;
    }


    @GetMapping("/getMeterAndMember")
    @CrossOrigin(origins="*")
    public Response getMeterAndMember() {
        Response<List<Meter>> response = new Response<>();
        response.setMsg("get meter success");
        response.setData(meterService.getMeterAndMember());
        return response;
    }

	@PostMapping("/update")
	@CrossOrigin(origins="*")
    public Response updateMeter(@RequestHeader("token") String token,
                                @RequestBody MeterRequest meterRequest) {
        Response response = new Response();
        Integer id = TokenUtil.getId(token);
        if (id == null) {
            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");
            return response;
        }
        User user = userMapper.selectUserById(id);
        if (user == null) {
            TokenUtil.clear(id, token);
            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");
            return response;
        }
        if (!roleManager.isAdmin(user.getId())) {
            response.setCode(ErrorCode.PERMISSION_ERROR);
            response.setMsg("permission error");
            return response;
        }
        if (StringUtils.isEmpty(meterRequest.getMeterName())) {
            response.setCode(ErrorCode.EMPTY_METERNAME);
            response.setMsg("empty meterName");
            return response;
        }
        if (StringUtils.isEmpty(meterRequest.getMeterDesc())) {
            response.setCode(ErrorCode.EMPTY_METERDESC);
            response.setMsg("empty meterDesc");
            return response;
        }
        if (StringUtils.isEmpty(meterRequest.getName())) {
            response.setCode(ErrorCode.EMPTY_MEMBERNAME);
            response.setMsg("empty memberName");
            return response;
        }
        if (StringUtils.isEmpty(meterRequest.getRoom())) {
            response.setCode(ErrorCode.EMPTY_ROOM);
            response.setMsg("empty room");
            return response;
        }

        if ((!meterRequest.getContact().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))) {
            response.setCode(ErrorCode.INVALID_METERCONTACT);
            response.setMsg("email invalid");
            return response;
        }

        try {
            if (meterService.updateMeter(meterRequest)) {
                response.setMsg("update meter success");
            } else {
                response.setCode(ErrorCode.DB_OPERATION_ERROR);
                response.setMsg("update meter faile");
            }
        } catch (Exception e) {
            response.setCode(ErrorCode.DB_OPERATION_ERROR);
            response.setMsg("update meter faile");
        }
        return response;
    }

	@DeleteMapping("/delete")
	@CrossOrigin(origins="*")
    public Response deleteMeter(@RequestHeader("token") String token,
                                @RequestParam("mid") int mid,
                                @RequestParam("bid") int bid) {
        Response response = new Response();
        Integer id = TokenUtil.getId(token);
        if (id == null) {
            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");
            return response;
        }
        User user = userMapper.selectUserById(id);
        if (user == null) {
            TokenUtil.clear(id, token);
            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");
            return response;
        }
        if (!roleManager.isAdmin(user.getId())) {
            response.setCode(ErrorCode.PERMISSION_ERROR);
            response.setMsg("permission error");
            return response;
        }

        try {
            if (meterService.deleteMeter(mid,bid)) {
                response.setMsg("delete success");
            } else {
                response.setCode(ErrorCode.DB_OPERATION_ERROR);
                response.setMsg("delete faile");
            }
        } catch (Exception e) {
            response.setCode(ErrorCode.DB_OPERATION_ERROR);
            response.setMsg("delete faile");
        }

        return response;
    }

	@PostMapping("/addMeter")
	@CrossOrigin(origins="*")
    public Response addMeter(@RequestHeader("token") String token,
                             @RequestParam("user_id") Integer user_id,
                             @RequestBody MeterRequest meterRequest){
        Response response = new Response();
        meterRequest.setUser_id(user_id);
        Integer id = TokenUtil.getId(token);
        if (id == null) {
            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");
            return response;
        }
        User user = userMapper.selectUserById(id);
        if (user == null) {
            TokenUtil.clear(id, token);
            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");
            return response;
        }
        if (!roleManager.isAdmin(user.getId())) {
            response.setCode(ErrorCode.PERMISSION_ERROR);
            response.setMsg("permission error");
            return response;
        }

        if (StringUtils.isEmpty(meterRequest.getMeterName())) {
            response.setCode(ErrorCode.EMPTY_METERNAME);
            response.setMsg("empty meterName");
            return response;
        }
        if (StringUtils.isEmpty(meterRequest.getMeterDesc())) {
            response.setCode(ErrorCode.EMPTY_METERDESC);
            response.setMsg("empty meterDesc");
            return response;
        }
        if (StringUtils.isEmpty(meterRequest.getName())) {
            response.setCode(ErrorCode.EMPTY_MEMBERNAME);
            response.setMsg("empty memberName");
            return response;
        }
        if (StringUtils.isEmpty(meterRequest.getRoom())) {
            response.setCode(ErrorCode.EMPTY_ROOM);
            response.setMsg("empty room");
            return response;
        }
        if ((!meterRequest.getContact().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))) {
            response.setCode(ErrorCode.INVALID_METERCONTACT);
            response.setMsg("email invalid");
            return response;
        }

        if (StringUtils.isEmpty(meterRequest.getPassword())) {
            response.setCode(ErrorCode.EMPTY_PASSWORD);
            response.setMsg("empty password");
            return response;
        }
        try {
            meterRequest = meterService.addMeter(meterRequest);
            if (meterRequest != null) {
                response.setMsg("add meter success");
                response.setData(meterRequest);
            } else {
                response.setCode(ErrorCode.DB_OPERATION_ERROR);
                response.setMsg("add meter fail");
            }
        } catch (Exception e) {
            response.setCode(ErrorCode.DB_OPERATION_ERROR);
            response.setMsg("add meter fail");
        }
        return response;
    }


}
