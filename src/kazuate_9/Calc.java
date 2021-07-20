package kazuate_9;

public class Calc {
	private String inputStr; //キーボードからの入力値を文字列で格納する
	private String answerStr = ""; //正解値をStringにして格納する変数
	private int n; //正解値の桁数
	private int [] answerInt; //正解値を要素ごとに1桁ずつ入れるint配列
	private int difficulty; //ゲームの難易度
	private int inputDigits; //入力値の桁数
	private int zeroCount; //「0」の桁の数
	private int correctDigits; //正解している桁の数
	private int duplicateCount; //正解値で桁の値が重複している数
	private int oshii; //「おしい」の数

	private final boolean TEST_MODE = false; //テストモード設定

	/* 難易度の選択 */
	public void selectDifficulty() {
		boolean loop = true;

		if(TEST_MODE) {
			System.out.println("テストモード:ON");
			System.out.println("正解値と入力値の桁数を表示します \n");
		}

		System.out.println("数当てゲームを始めます。 \n");
		while(loop) {
			System.out.println("難易度を選択してください");
			System.out.print("1:EASY  2:NORMAL  3:HARD>");
			this.difficulty = new java.util.Scanner(System.in).nextInt();
			switch(this.difficulty) {
				case 1: //EASY
					this.n = 2;
					System.out.println("EASYが選択されました");
					loop = false;
					break;
				case 2: //NORMAL
					this.n = 3;
					System.out.println("NORMALが選択されました");
					loop = false;
					break;
				case 3: //HARD
					this.n  = 4;
					System.out.println("HARDが選択されました");
					loop = false;
					break;
				default:
					System.out.println("1~3の整数値を入力して下さい");
			}
		}
		System.out.println("正解は" + this.n + "桁です \n");
	}

	/* 正解の値を設定する */
	public void setAnswer() {
		this.answerStr = "";
		this.answerInt = new int [this.n]; //要素数=桁数のint配列を生成
		for(int j = 0; j < this.n; j++) { //桁数分ループ
			this.answerInt[j] = new java.util.Random().nextInt(9) + 1; //各要素に1~9までの整数を入れる
			this.answerStr += String.valueOf(answerInt[j]); //各要素を文字列にして繋げる
		}
		if(TEST_MODE) {
			System.out.println("【テスト用】正解値: " + this.answerStr + "\n");
		}
	}

	/* 正解値の各桁の重複を調べる */
	public boolean checkDuplicate() {
		duplicateCount =0; //初期化

		//重複があった場合duplicateCountをインクリメントしてbreak
		loop: for(int i = 0; i < this.n - 1; i++) {
			for(int j = i + 1; j < this.n; j++) {
				if(this.answerInt[i] == this.answerInt[j]) {
					duplicateCount++;
					break loop;
				}
			}
		}
		if(duplicateCount >0) {
			if(TEST_MODE) {
				System.out.println("【テストメッセージ】 正解値重複: 値を再生成");
			}
			return true; //ループで再度正解値を生成する所まで戻る
		} else {
			return false; //正解値生成のループを抜ける
		}
	}

	/* 回答をキーボード入力から受取るメソッド */
	public void setInput() {
		System.out.println("数値を入力して下さい");
		System.out.print(this.n + "桁の数字 各桁の範囲1~9>");
		this.inputStr = new java.util.Scanner(System.in).nextLine(); //Stringで入力を受取る

	}

	/* 入力値のエラーチェックをするメソッド */
	public  boolean errorCheckInput() {
		this.inputDigits = inputStr.length(); //入力値の桁数をinputDigitsに代入

		if(TEST_MODE) {
		System.out.println("【テストメッセージ】入力値の桁数: " + this.inputDigits);
		}

		this.zeroCount = 0; //このメソッドを呼び出す毎に初期化する

		//0が入っている桁の数の確認
		for(int j = 0; j < inputDigits ;j++) {
			if(inputStr.substring(j, j + 1).equals("0")) {
				this.zeroCount++;
			}
		}

		//条件を満たさない場合、メッセージを表示後、再度入力させる
		if(zeroCount != 0 && inputDigits != this.n) {
			System.out.println("入力値が" + this.n + "桁ではありません");
			System.out.println("入力値に0が含まれています\n");
			return true;

		}else if(zeroCount != 0 && inputDigits == this.n) {
			System.out.println("入力値に0が含まれています\n");
			return true;

		}else if(zeroCount == 0 && inputDigits != this.n) {
			System.out.println("入力値が" + this.n + "桁ではありません\n");
			return true;
		} else {
			//条件を満たす値が入力された場合のみ,falseを返し、メインメソッド内のwhileループを抜ける
			return false;
		}
	}

	/* 正解している桁の数を調べる */
	public  void researchCorrectDigits() {
		this.correctDigits = 0; //このメソッドが開始する度に変数を初期化
		for(int i = 0; i < this.n; i++) {
			if(this.inputStr.substring(i, i + 1).equals(this.answerStr.substring(i, i + 1))) {
				this.correctDigits++;
			}
		}
	}

	/* 入力値の正誤判定 */
	public boolean compare(int count) {
		/* 結果の表示(正解時,falseを返す) */
		if(this.correctDigits == this.n) {
			System.out.println("正解です");
			System.out.println("正解までにかかった入力回数:" + (count + 1) + "回 \n");
			return false;

		} else {
			//「oshii」変数の値には正解の重複の数も含まれているため、「oshii -correctDigits」が「おしい」の値となる
			System.out.println("部分正解:" + correctDigits + " おしい: " + oshii + "\n");
			return true; //不正解なのでループを続ける
		}
	}

	/* 「おしい」の数を調べる */
	public void researchOshii() {
		this.oshii =0; //初期化

		//n桁×n桁で2重ループ
		for(int i = 0; i < this.n; i++) {
			for(int j = 0; j < this.n; j++) {
				if(this.inputStr.substring(j, j + 1).equals(this.answerStr.substring(j, j + 1))) {
					continue;//正解の桁目のループはスキップする（「おしい」に含めない）
				} else if(this.inputStr.substring(i, i + 1).equals(this.answerStr.substring(j, j + 1))) {
					this.oshii++;
				}
			}
		}
	}



}

