package kazuate_9;

public class Main {

	public static void main(String[] args) {
		/* インスタンスの生成 */
		Calc c = new Calc();

		/* 変数の設定 */
		boolean loop1 = true;
		boolean loop2 = true;
		boolean loop3 = true;
		int count = 0; //正解までにかかった入力回数のカウント

		/* 難易度と正解値の桁数の設定 */
		c.selectDifficulty();

		/* 正解値の生成 */
		while(loop1) { //仕様を満たす正解値が生成されるまでループ
			c.setAnswer();
	        loop1 = c.checkDuplicate();
		}

		/* 正解するまでループし続ける */
		while(loop2) { //正解値が入力されるまでループさせる

			while(loop3) { //入力値チェックをPassするまでループさせる
				c.setInput(); //入力値を設定
				loop3 = c.errorCheckInput(); //入力値のエラー確認
			}

			c.researchCorrectDigits(); //正解値と一致している桁の数を調べる
			c.researchOshii(); //「おしい」の数を調べる

			/* 正誤の判定と結果表示 */
			loop2 = c.compare(count); //全桁一致する時だけ戻り値でfalseを返し、whileループを抜ける)
			loop3 = true; //不正解でloop2から再度ループする場合、入力から再開したいのでloop3を再度trueにしてループに入るようにする
			count++;
		}
	}
}
