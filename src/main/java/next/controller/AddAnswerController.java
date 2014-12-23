package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class AddAnswerController extends AbstractController{
	private AnswerDao answerDao = new AnswerDao();
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Long questionId = Long.parseLong(request.getParameter("questionId"));
		Answer newAnswer = new Answer(request.getParameter("writer"),
				request.getParameter("contents"), questionId);
		answerDao.insert(newAnswer);
		questionDao.increaseCommentCount(questionId);
		ModelAndView mav = jstlView("redirect:/show.next?questionId=" + questionId);
		return mav;
	}
	
}
