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

import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.service.UsersService;

@Controller
public class PasswordResetConttoller {
		final static Logger logger = LoggerFactory.getLogger(LoginController.class);

		@Autowired
		private UsersService usersService;

	@RequestMapping(value = "/passwordReset", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	public String passwordReset(Model model) {
		return "passwordReset";
	}

	/**
	 * 新規アカウント作成
	 *
	 * @param email            メールアドレス
	 * @param password         パスワード
	 * @param passwordForCheck 確認用パスワード
	 * @param model
	 * @return ホーム画面に遷移
	 */
	 //テスト
	@Transactional
	@RequestMapping(value = "/passwordReset", method = RequestMethod.POST)
	public String passwordReset(Locale locale, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("passwordForCheck") String passwordForCheck,
			Model model) {
		// デバッグ用ログ
		logger.info("Welcome passwordReset! The client locale is {}.", locale);

		// バリデーションチェック、パスワード一致チェック（タスク１）
		/*パスワードチェック*/
		if (password.length() >= 8 && password.matches("^[A-Za-z0-9]+$")) {
			if (password.equals(passwordForCheck)) {/*確認用パスワード*/
				// パラメータで受け取ったアカウント情報をDtoに格納する。
				UserInfo userInfo = new UserInfo();
				userInfo.setEmail(email);
				userInfo.setPassword(password);
				usersService.resetUser(userInfo);
			} else {
				model.addAttribute("errorMessage", "パスワードが一致しません。");
				return "passwordReset";
			}
		} else {
			model.addAttribute("errorMessage", "パスワードは8文字以上かつ半角英数字に設定してください。");
			return "passwordReset";
		}
		return "redirect:/login";
	}

}
