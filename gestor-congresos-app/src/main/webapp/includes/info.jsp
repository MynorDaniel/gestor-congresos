<%
    String info = (String) request.getAttribute("infoAtributo");
    if (info != null) {
%>
    <div class="alert alert-success text-center rounded-0 mb-0" role="alert">
        <%= info %>
    </div>
<% } %>
