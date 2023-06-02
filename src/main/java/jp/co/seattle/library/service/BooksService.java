package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 * booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 書籍リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookInfo> getBookList() {

		// TODO 書籍名の昇順で書籍情報を取得するようにSQLを修正（タスク３）
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY title ASC;",
				new BookInfoRowMapper());

		return getedBookList;
	}
	
	
	
	public List<BookInfo> sortGetBookListAsc() {

		// TODO 書籍名の昇順で書籍情報を取得するようにSQLを修正（タスク３）追加実装
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY title ASC;",
				new BookInfoRowMapper());

		return getedBookList;
	}

	public List<BookInfo> sortGetBookListDesc() {
		// 降順
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY title DESC ",
				new BookInfoRowMapper());
		return getedBookList;
	}

	public List<BookInfo> sortBookListPlDateAsc() {
		// 著者名順
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY publish_date ASC ",
				new BookInfoRowMapper());
		return getedBookList;
	}

	public List<BookInfo> sortBookListPlDateDesc() {
		// 出版日順
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY publish_date DESC ",
				new BookInfoRowMapper());
		return getedBookList;
	}

	/**
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public BookDetailsInfo getBookInfo(int bookId) {
		String sql = "SELECT id, title, author, publisher, publish_date, classification, evaluation, isbn, description, thumbnail_url, thumbnail_name FROM books WHERE books.id = ? ORDER BY title ASC;";
    
		BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper(), bookId);

		return bookDetailsInfo;
	}
	

	/**
	 * 書籍を登録する
	 *
	 * @param bookInfo 書籍情報
	 * @return bookId 書籍ID
	 */
	public int registBook(BookDetailsInfo bookInfo) {
		// TODO 取得した書籍情報を登録し、その書籍IDを返却するようにSQLを修正（タスク４）
		
		String sql = "INSERT INTO books(title, author, publisher, publish_date, classification, thumbnail_name, thumbnail_url, evaluation, isbn, description, reg_date, upd_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now()) RETURNING id;";

		int bookId = jdbcTemplate.queryForObject(sql, int.class, bookInfo.getTitle(), bookInfo.getAuthor(),
				bookInfo.getPublisher(), bookInfo.getPublishDate(), bookInfo.getClassification(),
				bookInfo.getThumbnailName(), bookInfo.getThumbnailUrl(), bookInfo.getEvaluation(), bookInfo.getIsbn(), bookInfo.getDescription());
		return bookId;
	}

	/**
	 * 書籍を削除する
	 * 
	 * @param bookId 書籍ID
	 */
	public void deleteBook(int bookId) {
		// TODO 対象の書籍を削除するようにSQLを修正（タスク6）
		String sql = "DELETE FROM books WHERE books.id = ?;";
		jdbcTemplate.update(sql, bookId);
	}

	/**
	 * 書籍情報を更新する
	 * 
	 * @param bookInfo
	 */
	public void updateBook(BookDetailsInfo bookInfo) {
		String sql;
		if (bookInfo.getThumbnailUrl() == null) {
			// TODO 取得した書籍情報を更新するようにSQLを修正（タスク５）
			sql = "UPDATE books SET title = ?, author = ?, publisher = ?, publish_date = ?, classification = ?, isbn = ?, description = ?, upd_date = now(), evaluation = ? WHERE id = ?;";
			jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
					bookInfo.getPublishDate(), bookInfo.getClassification(), bookInfo.getIsbn(),
					bookInfo.getDescription(), bookInfo.getEvaluation(), bookInfo.getBookId());
		} else {
			// TODO 取得した書籍情報を更新するようにSQLを修正（タスク５）
			sql = "UPDATE books SET title = ?, author = ?, publisher = ?, publish_date = ?, classification = ?, thumbnail_name = ?, thumbnail_url = ?, isbn = ?, description = ?, upd_date = now(), evaluation = ? WHERE id = ?;";
			jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
					bookInfo.getPublishDate(), bookInfo.getClassification(), bookInfo.getThumbnailName(),
					bookInfo.getThumbnailUrl(), bookInfo.getIsbn(), 
					bookInfo.getDescription(), bookInfo.getEvaluation(), bookInfo.getBookId());
		}
	}

	public List<BookInfo> getSearchList(String title) {
		// 検索をかけて、得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedSearchList = jdbcTemplate.query(
				"SELECT * FROM books WHERE title like concat('%',?,'%') ORDER BY title;",
				new BookInfoRowMapper(), title);
		return getedSearchList;
	}

	public List<BookInfo> getBoysList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedBoysList = jdbcTemplate.query(
				"SELECT * FROM books WHERE classification = '少年漫画' ORDER BY title;",
				new BookInfoRowMapper());
		return getedBoysList;
	}

	public List<BookInfo> getGirlsList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedGirlsList = jdbcTemplate.query(
				"SELECT * FROM books WHERE classification = '少女漫画' ORDER BY title;",
				new BookInfoRowMapper());
		return getedGirlsList;
	}

	public List<BookInfo> getNovelList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedNovelList = jdbcTemplate.query(
				"SELECT * FROM books WHERE classification = '小説' ORDER BY title;",
				new BookInfoRowMapper());
		return getedNovelList;
	}

	public List<BookInfo> getPictureList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedPictureList = jdbcTemplate.query(
				"SELECT * FROM books WHERE classification = '絵本' ORDER BY title;",
				new BookInfoRowMapper());
		return getedPictureList;
	}

	public List<BookInfo> getPictorialList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedPictorialList = jdbcTemplate.query(
				"SELECT * FROM books WHERE classification = '図鑑' ORDER BY title;",
				new BookInfoRowMapper());
		return getedPictorialList;
	}

	public List<BookInfo> getMemoryList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedMemoryList = jdbcTemplate.query(
				"SELECT * FROM books WHERE classification = '思い出' ORDER BY title;",
				new BookInfoRowMapper());
		return getedMemoryList;
	}

	public List<BookInfo> getChoiceList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedChoiceList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY title;",
				new BookInfoRowMapper());
		return getedChoiceList;
	}

	/**
	 * お気に入り書籍を表示
	 */
	public List<BookInfo> getFavBookList() {
		// お気に入り書籍を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedFavBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE favorit = 'like' ORDER BY title;",
				new BookInfoRowMapper());
		return getedFavBookList;
	}

	/**
	 * お気に入り登録してDBにおくる
	 * →あくまでも登録だけ
	 * →戻り値のないvoid
	 */
	public void favoriteList(int bookId) {
		String sql = "UPDATE books SET favorit = 'like' WHERE id = ?;";
		jdbcTemplate.update(sql, bookId);
	}

	public void unFavoriteList(int bookId) {
		String sql = "UPDATE books SET favorit = 'unlike' WHERE id = ?";
		jdbcTemplate.update(sql, bookId);
	}

	
	
	
	public List<BookInfo> getFavSearchList(String title) {
		// 検索をかけて、得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedFavSearchList = jdbcTemplate.query(
				"SELECT * FROM books WHERE title like concat('%',?,'%') ORDER BY title;",
				new BookInfoRowMapper(), title);
		return getedFavSearchList;
	}

	public List<BookInfo> getFavBoysList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedFavBoysList = jdbcTemplate.query(
				"SELECT * FROM books WHERE classification = '少年漫画' ORDER BY title;",
				new BookInfoRowMapper());
		return getedFavBoysList;
	}

	public List<BookInfo> getFavGirlsList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedFavGirlsList = jdbcTemplate.query(
				"SELECT * FROM books WHERE classification = '少女漫画' ORDER BY title;",
				new BookInfoRowMapper());
		return getedFavGirlsList;
	}

	public List<BookInfo> getFavNovelList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedFavNovelList = jdbcTemplate.query(
				"SELECT * FROM books WHERE classification = '小説' ORDER BY title;",
				new BookInfoRowMapper());
		return getedFavNovelList;
	}

	public List<BookInfo> getFavPictureList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedFavPictureList = jdbcTemplate.query(
				"SELECT * FROM books WHERE classification = '絵本' ORDER BY title;",
				new BookInfoRowMapper());
		return getedFavPictureList;
	}

	public List<BookInfo> getFavPictorialList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedFavPictorialList = jdbcTemplate.query(
				"SELECT * FROM books WHERE classification = '図鑑' ORDER BY title;",
				new BookInfoRowMapper());
		return getedFavPictorialList;
	}

	public List<BookInfo> getFavMemoryList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）
		List<BookInfo> getedFavMemoryList = jdbcTemplate.query(
				"SELECT * FROM books WHERE classification = '思い出' ORDER BY title;",
				new BookInfoRowMapper());
		return getedFavMemoryList;
	}

	public List<BookInfo> getFavChoiceList() {
		// セレクトボックスから得たい書籍情報を取得するようにSQLを修正（追加実装）（選択の場合）
		List<BookInfo> getedFavChoiceList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY title;",
				new BookInfoRowMapper());
		return getedFavChoiceList;
	}

	/**
	 * 書籍をお気に入りに追加する
	 */
	/**
	 * 書籍をお気に入りに登録する
	 *
	 * @param bookInfo 書籍情報
	 * @return bookId 書籍ID
	 */
	public int favoriteBook(BookDetailsInfo bookInfo) {
		// TODO 取得した書籍情報を登録し、その書籍IDを返却するようにSQLを修正（タスク４）
		//String sql = "INSERT INTO books(title, author, publisher, publish_date, thumbnail_name, thumbnail_url, isbn, description, reg_date, upd_date) VALUES(?,?,?,?,?,?,?,?, now(),now()) RETURNING id;";
		String sql = "INSERT INTO books(title, author, publisher, publish_date, classification, thumbnail_name, thumbnail_url, isbn, description, evaluation, favorit, reg_date, upd_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now()) RETURNING id;";

		int bookId = jdbcTemplate.queryForObject(sql, int.class, bookInfo.getTitle(), bookInfo.getAuthor(),
				bookInfo.getPublisher(), bookInfo.getPublishDate(), bookInfo.getClassification(),
				bookInfo.getThumbnailName(), bookInfo.getThumbnailUrl(), 
				bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getEvaluation(),bookInfo.getFavorit());
		return bookId;
	}
	
	
	
//	/**
//	 * 評価機能の追加実装
//	 * @param bookId
//	 */
//	public void zrEvaluationList(int bookId) {
//		String sql = "UPDATE books SET evaluation = '0' WHERE id = ?;";
//		jdbcTemplate.update(sql, bookId);
//	}
//	public void fsEvaluationList(int bookId) {
//		String sql = "UPDATE books SET evaluation = '1' WHERE id = ?;";
//		jdbcTemplate.update(sql, bookId);
//	}
//
//	public void scEvaluationList(int bookId) {
//		String sql = "UPDATE books SET evaluation = '2' WHERE id = ?";
//		jdbcTemplate.update(sql, bookId);
//	}
//	public void thEvaluationList(int bookId) {
//		String sql = "UPDATE books SET evaluation = '3' WHERE id = ?";
//		jdbcTemplate.update(sql, bookId);
//	}
//	public void foEvaluationList(int bookId) {
//		String sql = "UPDATE books SET evaluation = '4' WHERE id = ?";
//		jdbcTemplate.update(sql, bookId);
//	}
//	public void fiEvaluationList(int bookId) {
//		String sql = "UPDATE books SET evaluation = '5' WHERE id = ?";
//		jdbcTemplate.update(sql, bookId);
//	}
}

