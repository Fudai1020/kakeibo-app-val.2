## usersテーブル

| カラム名         | データ型        | 制約                       | 説明          |
|---------------|--------------|----------------------------|---------------|
| user_id       | INT          | PRIMARY KEY,AUTO_INCREMENT | ユーザID         |
| user_name     | VARCHAR(50)  |                            | ユーザ名         |
| user_email    | VARCHAR(255) | NOT NULL, UNIQUE           | メールアドレス       |
| password_hash | VARCHAR(60)  | NOT NULL                   | パスワード(ハッシュ値) |
| user_memo     | VARCHAR(255) |                            | ユーザメモ         |
| created_at    | DATETIME     | NOT NULL                   | 作成日        |

## incomesテーブル

| カラム名         | データ型         | 制約                                  | 説明     |
|---------------|---------------|---------------------------------------|----------|
| income_id     | INT           | PRIMARY KEY,AUTO_INCREMENT            | 収入ID   |
| income_name   | VARCHAR(50)   | NOT NULL                              | 収入名   |
| income_amount | DECIMAL(10,2) | NOT NULL                              | 収入金額 |
| income_memo   | VARCHAR(255)  |                                       | 収入メモ   |
| is_private    | BOOLEAN       | NOT NULL                              | 公開設定 |
| income_date   | DATETIME      | NOT NULL                              | 収入日   |
| created_at    | DATETIME      | NOT NULL                              | 作成日   |
| user_id       | INT           | FOREIGN KEY REFERENCES users(user_id) | ユーザID    |

## paymentsテーブル
| カラム名          | データ型         | 制約                                           | 説明     |
|----------------|---------------|------------------------------------------------|----------|
| payment_id     | INT           | PRIMARY KEY,AUTO_INCREMENT                     | 支出ID   |
| payment_name   | VARCHAR(50)   | NOT NULL                                       | 支出名   |
| payment_amount | DECIMAL(10,2) | NOT NULL                                       | 支出金額 |
| payment_memo   | VARCHAR(255)  |                                                | 支出メモ   |
| is_private     | BOOLEAN       | NOT NULL                                       | 公開設定 |
| payment_date   | DATETIME      | NOT NULL                                       | 支出日   |
| created_at     | DATETIME      | NOT NULL                                       | 作成日   |
| user_id        | INT           | FOREIGN KEY REFERENCES users(user_id)          | ユーザID    |
| category_id    | INT           | FOREIGN KEY REFERENCES categories(category_id) | カテゴリID   |

## categoriesテーブル
| カラム名         | データ型       | 制約                       | 説明   |
|---------------|-------------|----------------------------|--------|
| category_id   | INT         | PRIMARY KEY,AUTO_INCREMENT | カテゴリID |
| category_name | VARCHAR(50) | NOT NULL                   | カテゴリ名 |

## savingsテーブル
| カラム名       | データ型       | 制約                                  | 説明   |
|-------------|-------------|---------------------------------------|--------|
| saving_id   | INT         | PRIMARY KEY,AUTO_INCREMENT            | 貯金ID |
| saving_name | VARCHAR(50) | NOT NULL                              | 貯金名 |
| created_at  | DATETIME    | NOT NULL                              | 作成日 |
| user_id     | INT         | FOREIGN KEY REFERENCES users(user_id) | ユーザID  |

## saving_allocations
| カラム名             | データ型         | 制約                                      | 説明       |
|-------------------|---------------|-------------------------------------------|----------|
| allocation_id     | INT           | PRIMARY KEY,AUTO_INCREMENT                | 振り分けID   |
| allocation_amount | DECIMAL(10,2) | NOT NULL                                  | 振り分け金額 |
| allocation_date   | DATE          | NOT NULL                                  | 振り分け月   |
| created_at        | DATETIME      | NOT NULL                                  | 作成日     |
| saving_id         | INT           | FOREIGN KEY REFERENCES savings(saving_id) | 貯金ID     |

## shareテーブル
| カラム名     | データ型       | 制約                                  | 説明       |
|-----------|-------------|---------------------------------------|------------|
| share_id  | INT         | PRIMARY KEY,AUTO_INCREMENT            | 共有ID     |
| user_id   | INT         | FOREIGN KEY REFERENCES users(user_id) | ユーザID      |
| start_at  | DATETIME    | NOT NULL                              | 共有開始日 |
| item_type | VARCHAR(20) | NOT NULL                              | 共有タイプ    |
| is_active | BOOLEAN     | NOT NULL                              | 共有判断   |
 