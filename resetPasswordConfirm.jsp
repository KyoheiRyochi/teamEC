<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/magenda.css">
<title>パスワード再設定確認</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<h1>パスワード再設定確認画面</h1>
	<s:form action="ResetPasswordCompleteAction">
		<table class="vertical-list-table">
			<tr>
				<th scope="row"><s:label value="ユーザーID" /></th>
				<td><s:property value="#session.loginId" /></td>
			</tr>
			<tr>
				<th scope="row"><s:label value="新しいパスワード" /></th>
				<td><s:property value="#session.concealedPassword" /></td>
			</tr>
		</table>
		<div class="submit_btn_box">
			<div>
				<s:form action="ResetPasswordACompleteAction">
					<s:submit value="パスワード再設定" class="submit_btn" />
				</s:form>
			</div>
		</div>
		<div class="submit_btn_box">
			<div>
				<s:form action="ResetPasswordAction">
					<s:submit value="戻る" class="back_btn" />
					<s:hidden name="backFlag" value="1" />
				</s:form>
			</div>
		</div>
	</s:form>
</body>
</html>