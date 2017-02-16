<%@ page import="org.danz.PayController" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>See transactions</title>
    <meta name="layout" content="main" />
</head>
<body>
<div>
    <h2>Transactions</h2>
    <br/>

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

    <b>Person:</b>

    <br/>
    <g:form action="viewTransactions" method="POST">
        <g:select name="accountName"
                  noSelection="${['null':'Select One...']}"
                  from="${accounts}"
                  optionKey="name" optionValue="name" />
    <br/>
    <br/>
    <b><input type="submit" value="Submit"/></b>
    </g:form>

    <g:if   test="${account != null}">
        <br/>
        <div>Name:  ${ account.name }</div>
        <div>Balance: £${ account.balance }</div>
        <br/>
        <div>
            Transactions:
            <br/>
            <g:if test="${!transactions.isEmpty()}">
                <table style="border: 1px solid black;">
                    <th style="border: 1px solid black;">From</th>
                    <th style="border: 1px solid black;">To</th>
                    <th style="border: 1px solid black;">Amount</th>
                    <g:each in="${transactions}" var="transaction">
                        <tr>
                            <td style="border: 1px solid black; padding: 5px;">${transaction.fromAccount}</td>
                            <td style="border: 1px solid black; padding: 5px;">${transaction.toAccount}</td>
                            <td style="border: 1px solid black; padding: 5px;">£${transaction.amount}</td>
                        </tr>
                    </g:each>
                </table>
            </g:if>
            <g:else>
                No transactions so far.
            </g:else>
        </div>
    </g:if>

    <br/>
    <br/>
    <br/>
    <a href="${createLink(controller: 'pay', action: 'index')}" >Go to payments</a>
</div>
</body>
</html>
