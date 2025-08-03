package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Review05 {

    public static void main(String[] args) {

        // キーボード入力受付
        System.out.print("検索キーワードを入力してください > ");
        String input = keyIn();

        try {
            // JDBCドライバの読み込み
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB接続・ステートメント・結果を try-with-resources で自動クローズ
            try (
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "kennichi10");

                PreparedStatement pstmt = con.prepareStatement(
                    "SELECT name, age FROM person WHERE id = ?");
            ) {
                // String → int に変換して ? にセット
                int id = Integer.parseInt(input);
                pstmt.setInt(1, id);

                // 実行と結果の取得
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("name");
                        int age = rs.getInt("age");
                        System.out.println(name);
                        System.out.println(age);
                    } else {
                        System.out.println("該当するデータがありません。");
                    }
                }

            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベース処理でエラーが発生しました。");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("数値への変換に失敗しました。");
            e.printStackTrace();
        }
    }

    // キーボードから文字列を受け取るメソッド
    private static String keyIn() {
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            line = reader.readLine();
        } catch (IOException e) {
            System.err.println("入力エラーが発生しました。");
        }
        return line;
    }
}
