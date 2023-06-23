package jp.co.seattle.library.controller;




import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
@Controller

public class DisplayBookInfoController {
	final static Logger logger = LoggerFactory.getLogger(DisplayBookInfoController.class);
	
	@Autowired
	private BooksService booksService;

	/**
	 * ボタンが押下されたら評価が入力されている対象書籍の評価情報を表示する
	 * 
	 * @param locale ロケール情報
	 * @param bookId 書籍ID
	 * @param evaluation 評価
	 * @param model モデル情報
	 * @return 遷移先画面名
	 */

	@Transactional
	@RequestMapping(value = "/displayBookInfo", method = RequestMethod.POST)
	public String displayBookInfo(Locale locale, @RequestParam("bookId") int bookId, RedirectAttributes redirectAttributes,
			Model model) {
		
		// 書籍情報取得
	    BookDetailsInfo bookInfo = booksService.getBookInfo(bookId);
	   

	    // 書籍名取得
	    String title = bookInfo.getTitle();

	    // 評価情報取得
	    String evaluation = bookInfo.getEvaluation();

	    // API呼び出す
	    String responseMessage = booksService.callAPI(bookInfo, title, evaluation, locale);

		if (responseMessage.equals("評価情報の出力完了")) {
			redirectAttributes.addFlashAttribute("succesMessage", responseMessage);
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", responseMessage);
		}

		return "redirect:/home";
	}

}
