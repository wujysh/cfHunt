package cn.edu.fudan.codeforces.ranking.controller;

import cn.edu.fudan.codeforces.ranking.entity.Problem;
import cn.edu.fudan.codeforces.ranking.service.ProblemService;
import cn.edu.fudan.codeforces.ranking.mysql.ProblemDifficultyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by wujy on 16-12-2.
 */
@Controller
public class ProblemController {

	private static final Logger logger = LoggerFactory.getLogger(ProblemController.class.getName());

	private final ProblemService problemService;

	private final ProblemDifficultyService problemDifficultyService;

	@Autowired
	public ProblemController(ProblemService problemService, ProblemDifficultyService problemDifficultyService) {
		this.problemService = problemService;
		this.problemDifficultyService = problemDifficultyService;
	}

	@RequestMapping("/problem")
	public ModelAndView listProblems(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "max", defaultValue = "20") Integer max) throws IOException {
		ModelAndView mav = new ModelAndView("problem/list");
		List<Problem> problems = problemService.listProblems(page, max);

		int totalPage = (int) Math.ceil(3118 / max), minPage = Math.max(1, page - 3),
				maxPage = Math.min(minPage + 6, totalPage);
		minPage = Math.max(1, maxPage - 6);
		mav.addObject("problems", problems);
		mav.addObject("page", page);
		mav.addObject("max", max);
		mav.addObject("totalPage", totalPage);
		mav.addObject("minPage", minPage);
		mav.addObject("maxPage", maxPage);
		return mav;
	}

	@RequestMapping("/problem/{contestId}/{index}")
	public ModelAndView problemInfo(@PathVariable String contestId, @PathVariable String index) throws IOException {
		ModelAndView mav = new ModelAndView("problem/info");
		Problem problem = problemService.getProblem(contestId, index);
		mav.addObject("problem", problem);
		return mav;
	}

	@RequestMapping("/problem/{contestId}/{index}")
	public String problemDifficultyByRank(@PathVariable String contestId, @PathVariable String index)
			throws IOException {
		Gson gson = new Gson();
		Map<String, Float> map = problemDifficultyService.listProblemDifficultyByRank(contestId, index);
		return gson.toJson(map);
	}
}
