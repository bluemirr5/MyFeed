package com.nnggstory.rss.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.nnggstory.rss.service.RssAnalyzeService;
import com.nnggstory.rss.service.RssBotService;

@Controller
public class RssController {
	
	@Autowired
	private RssBotService rssbotService;
	@Autowired
	private RssAnalyzeService rssAnalyzeService;
	
	@RequestMapping("/hostView")
	public String getHostListAndFormView(
			@RequestParam(required = false) Integer pageNum, 
			@RequestParam(required = false) Integer pageSize,
			Model model
			) {
		if(pageNum == null) {
			pageNum = 1;
		}
		if(pageSize == null) {
			pageSize = 5;
		}
		model.addAttribute("totalSize", rssbotService.getHostTotalSize());
		model.addAttribute("hostList", rssbotService.getHostList(pageNum, pageSize));
		return "hostView";
	}
	
	@RequestMapping("/insertHost")
	public ModelAndView insertHost(
			@RequestParam(required = true) String hostUrl,
			HttpServletRequest request,
			Model model
			) {
		rssbotService.insertHost(hostUrl);
		
		RedirectView rv = new RedirectView(request.getContextPath()+"/hostView");
		return new ModelAndView(rv);
	}
	
	@RequestMapping("/insertHostbyFile")
	public ModelAndView insertHostbyFile(
			Model model,
			HttpServletRequest request
			) throws Exception {
		MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest) request;
		MultipartFile rawDataFile = multiPartRequest.getFile("excelFile");
		rssbotService.insertHost(rawDataFile);
		RedirectView rv = new RedirectView(request.getContextPath()+"/hostView");
		
		if(rawDataFile.isEmpty()) {
			return new ModelAndView(rv);
		}
		
		return new ModelAndView(rv);
	}
	
	@RequestMapping("/gleanArticle")
	public String gleanArticle() {
		rssbotService.gleanArticle();
		return "ok";
	}
	
	@RequestMapping("/analyze")
	public String analyzeAll() throws Exception {
		rssAnalyzeService.analyzeAllHost();
		return "ok";
	}
	
	@RequestMapping("/cleanArticleFromHtml")
	public String cleanArticleFromHtml() {
		rssbotService.cleanArticleFromHtml();
		return "ok";
	}
	
	@RequestMapping("/article")
	public String getArticleList(
			@RequestParam(required = false) Integer pageNum, 
			@RequestParam(required = false) Integer pageSize,
			Model model
			) {
		if(pageNum == null) {
			pageNum = 1;
		}
		if(pageSize == null) {
			pageSize = 5;
		}
		
		model.addAttribute("totalSize", rssbotService.getArticleTotalSize());
		model.addAttribute("articleList", rssbotService.getArticleList(pageNum, pageSize));
		return "article_list";
	}
	
	@RequestMapping("/searchArticle")
	public String getSearchArticleList(
			@RequestParam(required = false) String keyword, 
			Model model
			) {
		model.addAttribute("totalSize", rssbotService.getArticleTotalSize());
		model.addAttribute("articleList", rssbotService.getSearchArticleList(keyword));
		return "article_list";
	}
}
