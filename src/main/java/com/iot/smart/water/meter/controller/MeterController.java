package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.WaterBill;
import com.iot.smart.water.meter.response.ErrorCode;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.service.MeterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iot/meter")
public class MeterController {

    @Autowired
    private MeterService mService;

    @Autowired
    private MeterMapper meterMapper;

    @GetMapping("/getWaterBill")
	@CrossOrigin(origins="*")
    public Response getWaterBill(@RequestParam("meterName") String meterName) {
        Response response = new Response();
        response.setData(mService.getWaterBill(meterName));
        response.setMsg("get water bill success");
        return response;
    }

    /**
	 * 
	 * @param memberName
	 * @param volume
	 */
	@PostMapping("/setMemberVolume")
	@CrossOrigin(origins="*")
    public Response setMemberVolume(@RequestParam("memberName") String memberName,
                                    @RequestParam("volume") long volume) {
        Response response = new Response();
        Meter meter = mService.getMeterByName(memberName);
        if (meter == null || volume <= 0) {
            response.setCode(ErrorCode.INVALID_PARAMS);
            response.setMsg("invalid params");
            return response;
        }
        if (!mService.setMemberVolume(meter, volume)) {
            response.setCode(ErrorCode.INVALID_SET_VOLUME_LIMIT);
            response.setMsg("set volume limit");
            response.setData(mService.setMemberVolume(meter,volume));
            return response;
        }
        response.setMsg("update member volume success");
        response.setData(mService.setMemberVolume(meter,volume));
        return response;
    }

    @GetMapping("/getMeters")
	@CrossOrigin(origins="*")
    public Response getMeters() {
        Response<List<Meter>> response = new Response<>();
        response.setMsg("get meter success");
        response.setData(mService.getMeters());
        return response;
    }

    /**
	 * 
	 * @param meter
	 */
	@PostMapping("/update")
	@CrossOrigin(origins="*")
    public Response updateMeter(@RequestBody Meter meter) {
        // TODO need auth in header to verify token?
        Response response = new Response();
        if (mService.updateMeter(meter) == null) {
            response.setCode(ErrorCode.INVALID_MID);
        } else if (StringUtils.isEmpty(meter.getMeterName())) {
            response.setCode(ErrorCode.EMPTY_METERNAME);
            response.setMsg("empty meterName");
            return response;
        } else if (StringUtils.isEmpty(meter.getMeterDesc())) {
            response.setCode(ErrorCode.EMPTY_METERDESC);
            response.setMsg("empty meterDesc");
            return response;
        } else if (StringUtils.isEmpty(meter.getMeterDesc())) {
            response.setCode(ErrorCode.EMPTY_MEMBERNAME);
            response.setMsg("empty memberName");
            return response;
        } else if (StringUtils.isEmpty(meter.getRoom())) {
            response.setCode(ErrorCode.EMPTY_ROOM);
            response.setMsg("empty room");
            return response;
        } else if ((!meter.getMemberContact().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))) {
            response.setCode(ErrorCode.INVALID_METERCONTACT);
            response.setMsg("email invalid");
            return response;
        }
        meterMapper.updateMeter(meter);
        response.setMsg("update meter success");
        response.setData(mService.updateMeter(meter));
        return response;
    }

    /**
	 * 
	 * @param mid
	 */
	@DeleteMapping("/delete")
	@CrossOrigin(origins="*")
    public Response deleteMeter(@RequestParam("mid") int mid) {
        // TODO need auth in header to verify token?
        Response response = new Response();
        if (mService.deleteMeter(mid) == null) {
            response.setCode(ErrorCode.INVALID_MID);
        } else {
            meterMapper.deleteMeterById(mService.deleteMeter(mid).getMid());
        }
        response.setMsg("delete success");
        return response;
    }

    /**
	 * 
	 * @param meter
	 */
	@PostMapping("/addMeter")
	@CrossOrigin(origins="*")
    public Response addMeter(@RequestBody Meter meter) {
        Response response = new Response();
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
        if (StringUtils.isEmpty(meter.getMeterDesc())) {
            response.setCode(ErrorCode.EMPTY_METERDESC);
            response.setMsg("empty memberName");
            return response;
        }
        if (StringUtils.isEmpty(meter.getRoom())) {
            response.setCode(ErrorCode.EMPTY_ROOM);
            response.setMsg("empty room");
            return response;
        }
        if ((!meter.getMemberContact().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))) {
            response.setCode(ErrorCode.INVALID_METERCONTACT);
            response.setMsg("email invalid");
            return response;
        }
        response.setMsg("add meter success");
        response.setData(mService.addMeter(meter));
        return response;
    }
}
