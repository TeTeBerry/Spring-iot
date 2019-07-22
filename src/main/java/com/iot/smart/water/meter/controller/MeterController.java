package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.MemberMapper;
import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.dao.UserMapper;
import com.iot.smart.water.meter.dao.VolumeMapper;
import com.iot.smart.water.meter.model.Member;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.User;
import com.iot.smart.water.meter.model.Volume;
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
    public Response getWaterBill(@RequestParam("meterName") String meterName) {
        Response response = new Response();
        response.setData(meterService.getWaterBill(meterName));
        response.setMsg("get water bill success");
        return response;
    }

	@PostMapping("/setVolume")
	@CrossOrigin(origins="*")
    public Response setVolume(@RequestHeader("token") String token,
                              @RequestParam("member_id") Integer member_id,
                              @RequestParam("meter_id") Integer meter_id,
                              @RequestParam("volume") long newVolumeNum) {
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
        if (!meterService.setMemberVolume(volumeBean, newVolumeNum)) {
            response.setCode(ErrorCode.INVALID_SET_VOLUME_LIMIT);
            response.setMsg("set volume limit");
            return response;
        }
        response.setMsg("update member volume success");
        return response;
    }

    @GetMapping("/getMeters")
	@CrossOrigin(origins="*")
    public Response getMeters() {
        Response<List<Meter>> response = new Response<>();
        response.setMsg("get meter success");
        response.setData(meterService.getMeters());
        return response;
    }

	@PostMapping("/update")
	@CrossOrigin(origins="*")
    public Response updateMeter(@RequestHeader("token") String token,
                                @RequestBody Meter meter) {
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

        if (meterService.updateMeter(meter) == null) {
            response.setCode(ErrorCode.INVALID_MID);
        } else if (StringUtils.isEmpty(meter.getMeterName())) {
            response.setCode(ErrorCode.EMPTY_METERNAME);
            response.setMsg("empty meterName");
            return response;
        } else if (StringUtils.isEmpty(meter.getMeterDesc())) {
            response.setCode(ErrorCode.EMPTY_METERDESC);
            response.setMsg("empty meterDesc");
            return response;
        }
//        else if ((!meter.getMemberContact().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))) {
//            response.setCode(ErrorCode.INVALID_METERCONTACT);
//            response.setMsg("email invalid");
//            return response;
//        }
        meterMapper.updateMeter(meter);
        response.setMsg("update meter success");
        response.setData(meterService.updateMeter(meter));
        return response;
    }

	@DeleteMapping("/delete")
	@CrossOrigin(origins="*")
    public Response deleteMeter(@RequestHeader("token") String token,
                                @RequestParam("mid") int mid) {
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

        if (meterService.deleteMeter(mid) == null) {
            response.setCode(ErrorCode.INVALID_MID);
        } else {
            meterMapper.deleteMeterById(meterService.deleteMeter(mid).getId());
        }
        response.setMsg("delete success");
        return response;
    }

	@PostMapping("/addMeter")
	@CrossOrigin(origins="*")
    public Response addMeter(@RequestHeader("token") String token,
                             @RequestBody Meter meter){
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

        if (StringUtils.isEmpty(meter.getMeterName())) {
            response.setCode(ErrorCode.EMPTY_METERNAME);
            response.setMsg("empty meterName");
            return response;
        }
        if (StringUtils.isEmpty(meter.getMeterDesc())) {
            response.setCode(ErrorCode.EMPTY_METERDESC);
            response.setMsg("empty meterDesc");
            return response;
        }
//        if ((!meter.getMemberContact().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))) {
//            response.setCode(ErrorCode.INVALID_METERCONTACT);
//            response.setMsg("email invalid");
//            return response;
//        }
        response.setMsg("add meter success");
        response.setData(meterService.addMeter(meter));
        return response;
    }
}
