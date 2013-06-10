package com.nnggstory.feedfactory.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nnggstory.feedfactory.model.DataUserModel;
import com.nnggstory.feedfactory.model.RssPulishChannelModel;
import com.nnggstory.feedfactory.model.ViewUserModel;
import com.nnggstory.feedfactory.service.FeedService;
import com.nnggstory.feedfactory.service.ManagementFeedService;

/**
 * 사용자 및 사용자 관련 feed 설정을 위한 컨트롤러 클래스
 * 
 * @author bluemirr5
 *
 */
@Controller
@RequestMapping("management")
public class ManagementFeedController {
	
	@Autowired
	private ManagementFeedService managementFeedService;
	
	@Autowired
	private FeedService feedService;
	
	/**
	 * 사용자의 정보를 반환한다.
	 * 
	 * @param userId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(
			value="/user/{userId}",
			method=RequestMethod.GET
			)
	public String getUser(
			@PathVariable String userId,
			Model model
			) throws Exception {
		ViewUserModel userViewModel = managementFeedService.getUser(userId);
		if(userViewModel != null) {
			model.addAttribute("user", userViewModel);
		}
		return "userDetailPage";
	}
	
	/**
	 * 사용자의 정보를 저장한다.
	 * 
	 * @param userId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(
			consumes={"application/xml","application/json"},
			value="/user",
			method=RequestMethod.POST
			)
	public String saveUser(
			@RequestBody DataUserModel userDataModel
			) throws Exception {
		managementFeedService.saveUser(userDataModel);
		return "userInsertComplete";
	}
	
	@RequestMapping(
			value="/rssa",
			method=RequestMethod.GET
			)
	public @ResponseBody Map<String, Object> getRss(
			@RequestParam(required=false) String userId,
			@RequestParam(required=false) String groupId
			) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		RssPulishChannelModel resultModel = feedService.getRssByUserNGroup(userId, groupId);
		map.put("response", resultModel);
		return map;
//		return "";
	}
}