# WebhookPlugin

## 概要

WebhookPluginは、Minecraftサーバー内からDiscord Webhookへメッセージを送信できるPaper/Spigot系プラグインです。

`config.yml` に登録名とWebhook URLを設定しておくことで、ゲーム内コマンドから指定したDiscordチャンネルへメッセージを送信できます。

Webhook送信時のHTTP通信は非同期処理で実行されるため、Discordへの通信待ちでサーバーのメイン処理を止めにくい構成になっています。

## 機能

- `/webhook [登録名] [内容]` でDiscord Webhookへメッセージを送信
- 登録名ごとに送信先Webhook URLを変更可能
- 複数のWebhookを `config.yml` で管理可能
- `/webhookplugin reload` で設定を再読み込み可能
- 権限によるコマンド制限
- HTTP通信を非同期処理で実行

## コマンド

### Webhook送信

```txt
/webhook [登録名] [内容]
```

使用例：

```txt
/webhook hook1 テスト送信です
/webhook staff サーバーで問題が発生しました
/webhook event イベントを開始します
```

登録名に対応したWebhook URLへ、指定した内容が送信されます。

---

### 設定再読み込み

```txt
/webhookplugin reload
```

`config.yml` を変更した後、サーバーを再起動せずに設定を反映できます。

## config.yml 設定例

初期状態ではWebhookは登録されていません。

```yml
webhooks: {}

messages:
  usage: "使い方: /webhook [登録名] [内容]"
  no-permission: "このコマンドを使う権限がありません。"
  unknown-webhook: "その登録名のWebhookは存在しません。"
  success: "Webhook送信処理を開始しました。"
  reload-success: "config.ymlを再読み込みしました。"
  reload-usage: "使い方: /webhookplugin reload"
```

Webhookを登録する場合は、以下のように設定してください。

```yml
webhooks:
  hook1:
    url: "https://discord.com/api/webhooks/..."

  staff:
    url: "https://discord.com/api/webhooks/..."

  event:
    url: "https://discord.com/api/webhooks/..."

messages:
  usage: "使い方: /webhook [登録名] [内容]"
  no-permission: "このコマンドを使う権限がありません。"
  unknown-webhook: "その登録名のWebhookは存在しません。"
  success: "Webhook送信処理を開始しました。"
  reload-success: "config.ymlを再読み込みしました。"
  reload-usage: "使い方: /webhookplugin reload"
```

この設定の場合、以下のように使用できます。

```txt
/webhook hook1 テスト送信です
/webhook staff スタッフ向け通知です
/webhook event イベント開始のお知らせです
```

## 注意

Webhook URLは外部に公開しないでください。

Webhook URLが漏れると、第三者がDiscordチャンネルへメッセージを送信できる可能性があります。

GitHubにアップロードする `src/main/resources/config.yml` には、本物のWebhook URLを書かないようにしてください。