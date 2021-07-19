<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Code</title>
    <link rel="stylesheet" href="/css/code_style.css">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@11.1.0/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@11.1.0/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
    <span id="load_date">${code.date}</span><br>
    <#if code.viewRestricted>
        <span id="views_restriction"><b>${code.views}</b> more views allowed.</span><br>
    </#if>
    <#if code.timeRestricted>
        <span id="time_restriction">The code will be available for <b>${code.time}</b> seconds.</span><br>
    </#if>
    <pre id="code_snippet"><code>${code.code}</code></pre>
</body>
</html>
