package me.example.webhookplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.util.Arrays;

public class WebhookPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getLogger().info("WebhookPlugin が有効化されました！");
    }

    @Override
    public void onDisable() {
        getLogger().info("WebhookPlugin が無効化されました！");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("webhook")) {

            if (!sender.hasPermission("webhookplugin.send")) {
                String noPermission = getConfig().getString("messages.no-permission", "このコマンドを使う権限がありません。");
                sender.sendMessage(noPermission);
                return true;
            }

            if (args.length < 2) {
                String usage = getConfig().getString("messages.usage", "使い方: /hoge [登録名] [内容]");
                sender.sendMessage(usage);
                return true;
            }

            String webhookName = args[0];
            String content = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            String webhookUrl = getConfig().getString("webhooks." + webhookName + ".url", "");

            if (webhookUrl == null || webhookUrl.isBlank() || webhookUrl.contains("ここに")) {
                String unknownWebhook = getConfig().getString("messages.unknown-webhook", "その登録名のWebhookは存在しません。");
                sender.sendMessage(unknownWebhook);
                return true;
            }

            sendDiscordMessage(webhookUrl, content);

            String success = getConfig().getString("messages.success", "Webhookを送信しました。");
            sender.sendMessage(success);

            return true;
        }

        if (command.getName().equalsIgnoreCase("webhookplugin")) {

            if (!sender.hasPermission("webhookplugin.reload")) {
                sender.sendMessage("このコマンドを使う権限がありません。");
                return true;
            }

            if (args.length < 1 || !args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage("使い方: /webhookplugin reload");
                return true;
            }

            reloadConfig();

            String reloadSuccess = getConfig().getString("messages.reload-success", "config.ymlを再読み込みしました。");
            sender.sendMessage(reloadSuccess);

            return true;
        }

        return false;
    }

    private void sendDiscordMessage(String webhookUrl, String message) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String json = "{\"content\":\"" + escapeJson(message) + "\"}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == 204 || responseCode == 200) {
                getLogger().info("Webhook送信に成功しました。");
            } else {
                getLogger().warning("Webhook送信に失敗しました。レスポンスコード: " + responseCode);
            }

            connection.disconnect();

        } catch (IOException e) {
            getLogger().warning("Webhook送信中にエラーが発生しました。");
            e.printStackTrace();
        }
    }

    private String escapeJson(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}