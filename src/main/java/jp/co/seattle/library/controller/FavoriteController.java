package jp.co.seattle.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class FavoriteController {
	final static Logger logger = LoggerFactory.getLogger(FavoriteController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 * 
	 * 
	 * お気に入り書籍のみ表示
	 */
	@RequestMapping(value = "/favoriteBook", method = RequestMethod.GET)
	public String favoriteBook(Model model) {
		//お気に入り登録した書籍の一覧情報を取得（タスク３）
		List<BookInfo> getedFavBookList = booksService.getFavBookList();

		if (getedFavBookList.isEmpty()) {
			model.addAttribute("resultMessage", "データが存在しません");
		} else {
			model.addAttribute("bookList", getedFavBookList);
		}
		return "favorite";
	}
	/**
	 * @param title
	 * @param model
	 * @return
	 * 
	 * お気に入りボタンを押した際、お気に入り画面を表示し直す
	 */
	 @RequestMapping(value = "/favorited", method = RequestMethod.GET)
	 public String favoriteView(Model model) {
	  return "redirect:/favoriteBook";
	 }

	@RequestMapping(value = "/favClass", method = RequestMethod.GET)
	public String classBook(@RequestParam("category") String classification, Model model) {
	//ジャンル分け（追加実装）
	//ジャンルに当てはまる書籍の一覧情報を取得（タスク３）
		if(classification.equals("少年漫画")) {
			List<BookInfo> books = booksService.getFavBoysList();
			model.addAttribute("bookList", books);
		}else if(classification.equals("少女漫画")) {
			List<BookInfo> books = booksService.getFavGirlsList();
			model.addAttribute("bookList", books);
		}else if(classification.equals("小説")) {
			List<BookInfo> books = booksService.getFavNovelList();
			model.addAttribute("bookList", books);
		}else if(classification.equals("絵本")) {
			List<BookInfo> books = booksService.getFavPictureList();
			model.addAttribute("bookList", books);
		}else if(classification.equals("図鑑")) {
			List<BookInfo> books = booksService.getFavPictorialList();
			model.addAttribute("bookList", books);
		}else if(classification.equals("思い出")) {
			List<BookInfo> books = booksService.getFavMemoryList();
			model.addAttribute("bookList", books);
		}else if(classification.equals("選択")) {
			List<BookInfo> books = booksService.getFavChoiceList();
			model.addAttribute("bookList", books);
			return "home";
		}
	return "home";
	}
}