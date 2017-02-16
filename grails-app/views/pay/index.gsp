<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Pay Some Person</title>
    <meta name="layout" content="main" />
</head>
<body>

<h2>Pay</h2>
<br/>

<g:if test="${success}">
    <div style="color: green">
        ${success}
    </div>

    <br/>
</g:if>

<g:if test="${!errors.isEmpty()}">
    <div style="color: red">
        ERRORS:
        <ul>
            <g:each in="${ errors }" var="error" status="i">
                <li>
                    ${ error }
                </li>
            </g:each>
        </ul>
    </div>
</g:if>


<g:form action="doPayment" method="POST">
    From account
    <g:select name="accountFrom"
              noSelection="${['null':'Select One...']}"
              from="${accounts}"
              optionKey="name" optionValue="name" />
    To account
    <g:select name="accountTo"
              noSelection="${['null':'Select One...']}"
              from="${accounts}"
              optionKey="name" optionValue="name" />
    Value
    <input type="text" name="amount" />
    <br/>
    <br/>
    <b><input type="submit" value="Submit"/></b>
    <br/>
    <br/>
    <a href="${createLink(controller: 'transaction', action: 'index')}" >Go to transactions</a>
</g:form>
</body>
</html>