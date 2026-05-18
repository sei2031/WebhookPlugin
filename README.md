# WebhookPlugin

## 概要

Minecraftサーバー内からDiscord Webhookへメッセージを送信できます。

`config.yml` に登録名とWebhook URLを設定しておくことで、コマンドから指定した登録名のDiscordチャンネルへメッセージを送信できます。

## 機能

- `/webhook [登録名] [内容]` でDiscord Webhookへメッセージを送信
- 登録名ごとに送信先チャンネルを変更可能
- 複数のWebhookを `config.yml` で管理可能
- `/webhookplugin reload` で設定を再読み込み可能
- 権限によるコマンド制限

## コマンド

### Webhook送信

```txt
/webhook [登録名] [内容]
```

使用例：

```txt
/webhook staff サーバーで問題が発生しました
/webhook event イベントを開始します
/webhook slot 大当たりが出ました
```

### 設定再読み込み

```txt
/webhookplugin reload
```

`config.yml` を変更した後、サーバーを再起動せずに設定を反映できます。

## config.yml 設定例

```yml
webhooks:
  hook1:
    url: "https://discord.com/api/webhooks/..."

  staff:
    url: "https://discord.com/api/webhooks/..."

messages:
  usage: "使い方: /webhook [登録名] [内容]"
  no-permission: "このコマンドを使う権限がありません。"
  unknown-webhook: "その登録名のWebhookは存在しません。"
  success: "Webhookを送信しました。"
  reload-success: "config.ymlを再読み込みしました。"
  reload-usage: "使い方: /webhookplugin reload"
```