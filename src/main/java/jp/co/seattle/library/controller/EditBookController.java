package jp.co.seattle.library.controller;

import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.commonutil.BookUtil;
import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

@Controller
public class EditBookController {
	final static Logger logger = LoggerFactory.getLogger(EditBookController.class);

	@Autowired
	private BooksService booksService;
	@Autowired
	private ThumbnailService thumbnailService;
	@Autowired
	private BookUtil bookUtil;

	@RequestMapping(value = "/editBook", method = RequestMethod.GET)
	public String transitionEdit(Locale locale, int bookId, Model model) {
		logger.info("Welcome EditBooks.java! The client locale is {}.", locale);
		model.addAttribute("bookInfo", booksService.getBookInfo(bookId));
		return "editBook";
	}

	
	/**
	 * 書籍情報を更新する
	 * 
	 * @param locale      ロケール情報
	 * @param title       書籍名
	 * @param author      著者名
	 * @param publisher   出版社
	 * @param publishDate 出版日
	 * @param classification　分類（追加実装）
	 * @param file        サムネイルファイル
	 * @param evaluation　評価（追加実装）
	 * @param isbn        ISBN
	 * @param description 説明文
	 * @param model       モデル
	 * @return 遷移先画面
	 */
	@Transactional
	@RequestMapping(value = "/updateBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String updateBook(Locale locale, @RequestParam("bookId") int bookId, @RequestParam("title") String title,
			@RequestParam("author") String author, @RequestParam("publisher") String publisher,
			@RequestParam("publishDate") String publishDate, @RequestParam("classification") String classification, 
			@RequestParam("evaluation") String evaluation, @RequestParam("isbn") String isbn, @RequestParam("description") String description, 
			@RequestParam("thumbnail") MultipartFile file,
			Model model) {
		logger.info("Welcome updateBook! The client locale is {}.", locale);

		// パラメータで受け取った書籍情報をDtoに格納する。
		BookDetailsInfo bookInfo = new BookDetailsInfo();
		bookInfo.setBookId(bookId);
		bookInfo.setTitle(title);
		bookInfo.setAuthor(author);
		bookInfo.setPublisher(publisher);
		bookInfo.setPublishDate(publishDate);
		bookInfo.setClassification(classification);
		bookInfo.setEvaluation(evaluation);
		bookInfo.setIsbn(isbn);
		bookInfo.setDescription(description);

		List<String> errorList = bookUtil.checkBookInfo(bookInfo);
		// errorListに一つでもエラーメッセージが入っていたら登録しない
		if (errorList.size() > 0) {
			model.addAttribute("bookInfo", bookInfo);
			model.addAttribute("errorList", errorList);
			return "editBook";
		}

		// クライアントのファイルシステムにある元のファイル名を設定する
		String thumbnail = file.getOriginalFilename();

		if (!file.isEmpty()) {
			try {
				// サムネイル画像をアップロード
				String fileName = thumbnailService.uploadThumbnail(thumbnail, file);
				// URLを取得
				String thumbnailUrl = thumbnailService.getURL(fileName);

				bookInfo.setThumbnailName(fileName);
				bookInfo.setThumbnailUrl(thumbnailUrl);

			} catch (Exception e) {
				// 異常終了時の処理
				logger.error("サムネイルアップロードでエラー発生", e);
				model.addAttribute("bookDetailsInfo", bookInfo);
				return "editBook";
			}
		}
		// 書籍情報を更新する
		booksService.updateBook(bookInfo);

		// 一覧画面に遷移する
		return "redirect:/home";
	}
	
	
	@RequestMapping(value = "/fvevaluation", method = RequestMethod.POST)
	public String fvevaluate(@RequestParam("bookId") int bookId, Model model) {
		//お気に入りをDBに登録する（追加実装） 
		//↓bookServiceの中のfavoriteListを実行する指示
		booksService.fiEvaluationList(bookId);
		return "redirect:/home";
	}
	@RequestMapping(value = "/fuevaluation", method = RequestMethod.POST)
	public String fuevaluate(@RequestParam("bookId") int bookId, Model model) {
		//お気に入りをDBに登録する（追加実装） 
		//↓bookServiceの中のfavoriteListを実行する指示
		booksService.foEvaluationList(bookId);
		return "redirect:/home";
	}
	@RequestMapping(value = "/thevaluation", method = RequestMethod.POST)
	public String thevaluate(@RequestParam("bookId") int bookId, Model model) {
		//お気に入りをDBに登録する（追加実装） 
		//↓bookServiceの中のfavoriteListを実行する指示
		booksService.thEvaluationList(bookId);
		return "redirect:/home";
	}
	@RequestMapping(value = "/twevaluation", method = RequestMethod.POST)
	public String twevaluate(@RequestParam("bookId") int bookId, Model model) {
		//お気に入りをDBに登録する（追加実装） 
		//↓bookServiceの中のfavoriteListを実行する指示
		booksService.scEvaluationList(bookId);
		return "redirect:/home";
	}
	@RequestMapping(value = "/onevaluation", method = RequestMethod.POST)
	public String onevaluate(@RequestParam("bookId") int bookId, Model model) {
		//お気に入りをDBに登録する（追加実装） 
		//↓bookServiceの中のfavoriteListを実行する指示
		booksService.fsEvaluationList(bookId);
		return "redirect:/home";
	}
	@RequestMapping(value = "/zeroevaluation", method = RequestMethod.POST)
	public String zeroevaluate(@RequestParam("bookId") int bookId, Model model) {
		//お気に入りをDBに登録する（追加実装） 
		//↓bookServiceの中のfavoriteListを実行する指示
		booksService.zrEvaluationList(bookId);
		return "redirect:/home";
	}

}
