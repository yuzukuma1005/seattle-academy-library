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
public class HomeController {
	final static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 * @param classification　分類
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String transitionHome(Model model) {
		//書籍の一覧情報を取得（タスク３）
		List<BookInfo> getedBookList = booksService.getBookList();

		if (getedBookList.isEmpty()) {
			model.addAttribute("resultMessage", "データが存在しません");
		} else {
			model.addAttribute("bookList", getedBookList);
		}
		return "home";
	}

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public String selectBook(@RequestParam("keyword") String title, Model model) {
		// 部分一致
		//検索条件に当てはまる書籍の一覧情報を取得（タスク３）
		List<BookInfo> books = booksService.getSearchList(title);
		model.addAttribute("bookList", books);
		return "home";
	}

	@RequestMapping(value = "/class", method = RequestMethod.GET)
	public String classBook(@RequestParam("category") String classification, Model model) {
	//ジャンル分け（追加実装）
	//ジャンルに当てはまる書籍の一覧情報を取得（タスク３）
		if(classification.equals("少年漫画")) {
			List<BookInfo> books = booksService.getBoysList();
			model.addAttribute("bookList", books);
		}else if(classification.equals("少女漫画")) {
			List<BookInfo> books = booksService.getGirlsList();
			model.addAttribute("bookList", books);
		}else if(classification.equals("小説")) {
			List<BookInfo> books = booksService.getNovelList();
			model.addAttribute("bookList", books);
		}else if(classification.equals("絵本")) {
			List<BookInfo> books = booksService.getPictureList();
			model.addAttribute("bookList", books);
		}else if(classification.equals("図鑑")) {
			List<BookInfo> books = booksService.getPictorialList();
			model.addAttribute("bookList", books);
		}else if(classification.equals("思い出")) {
			List<BookInfo> books = booksService.getMemoryList();
			model.addAttribute("bookList", books);
		}else if(classification.equals("選択")) {
			List<BookInfo> books = booksService.getChoiceList();
			model.addAttribute("bookList", books);
			return "home";
		}
	return "home";
	}
	
	@RequestMapping(value = "/fvbutton", method = RequestMethod.GET)
	public String favoriteBook(@RequestParam("bookId") int bookId, Model model) {
		//お気に入りをDBに登録する（追加実装） 
		//↓bookServiceの中のfavoriteListを実行する指示
		booksService.favoriteList(bookId);
		return "redirect:/home";
	}
	@RequestMapping(value = "/fvtbutton", method = RequestMethod.GET)
	public String unFavoriteBook(@RequestParam("bookId") int bookId, Model model) {
		//お気に入りをDBに登録する（追加実装） 
		//↓bookServiceの中のfavoriteListを実行する指示
		booksService.unFavoriteList(bookId);
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/sortOrders", method = RequestMethod.GET)
	public String sort(Model model, @RequestParam(name="sortOrder") String sort) {
	//並べ替え機能（追加実装）
		if (sort.equals("sortASC")){
			List<BookInfo> books = booksService.sortGetBookListAsc();
			model.addAttribute("bookList", books);
		}else if(sort.equals("sortDESC")) {
			List<BookInfo> books = booksService.sortGetBookListDesc();
			model.addAttribute("bookList", books);
		}else if(sort.equals("sortPlASC")) {
			List<BookInfo> books = booksService.sortBookListPlDateAsc();
			model.addAttribute("bookList", books);
		}else if(sort.equals("sortPlDESC")) {
			List<BookInfo> books = booksService.sortBookListPlDateDesc();
			model.addAttribute("bookList", books);
		}
	return "home";
	}
	
	
}