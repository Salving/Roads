<html>
<body>
    <p>
        Широта: ${latitude}
        Долгота: ${longitude}
    </p>

    <ul>
        <#list notes as note>
            <li>${note.text}</li>
        </#list>
    </ul>

</body>
</html>