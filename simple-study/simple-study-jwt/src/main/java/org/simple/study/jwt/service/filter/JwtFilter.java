package org.simple.study.jwt.service.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import org.simple.study.jwt.service.UserService;
import org.simple.study.jwt.service.util.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {
	private static Logger log = LoggerFactory.getLogger(JwtFilter.class);
	private static final PathMatcher pathMatcher = new AntPathMatcher();

	// 允许匿名访问的路径
	private List<String> anonymousPaths = new ArrayList<String>();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("*********************************过滤器被使用**************************");

		try {
			if (!anonymousAccess(request)) {
				// 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
				String token = request.getHeader("Authorization");
				String userName = JwtUtil.getUserName(token);
				String password = UserService.getPasswordByUserName(userName);
				// 检查jwt令牌, 如果令牌不合法或者过期, 里面会直接抛出异常, 下面的catch部分会直接返回
				JwtUtil.verify(token, userName, password);
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			return;
		}
		// 如果jwt令牌通过了检测, 那么就把request传递给后面的RESTful api
		filterChain.doFilter(request, response);
	}

	/**
	 * 允许匿名访问
	 * 
	 * @param request
	 * @return
	 */
	private boolean anonymousAccess(HttpServletRequest request) {
		if (anonymousPaths.size() == 0) {
			initAnonymousPaths();
		}
		for (String path : anonymousPaths) {
			if (pathMatcher.match(path, request.getServletPath())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 自定义添加允许匿名访问的路径
	 */
	public void initAnonymousPaths() {
		List<String> pathList = new ArrayList<String>();
		pathList.add("/login/**");
		pathList.add("/v2/**");
		pathList.add("/webjars/**");
		pathList.add("/swagger-resources/**");
		pathList.add("/swagger-ui.html/**");
		pathList.add("/doc.html/**");
		this.anonymousPaths = pathList;
	}

}