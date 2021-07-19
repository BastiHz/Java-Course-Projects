<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create</title>
    <link rel="stylesheet" href="/css/create_style.css">
    <script src="/js/submit_button.js"></script>
    <link rel="stylesheet" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@11.1.0/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@11.1.0/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
    <textarea id="code_snippet" rows="25" autofocus placeholder="write your code here"></textarea>
    <br>
    <table>
        <tr>
            <td><label for="time_restriction">Time restriction in seconds:</label></td>
            <td><input id="time_restriction" type="text" pattern="\d+" value="0"></td>
        </tr>
        <tr>
            <td><label for="views_restriction">Views restriction:</label></td>
            <td><input id="views_restriction" type="text" pattern="\d+" value="0"></td>
        </tr>
        <tr>
            <td></td>
            <td align="right"><button id="send_snippet" onclick="send()">Submit</button></td>
        </tr>
    </table>
</body>
</html>
