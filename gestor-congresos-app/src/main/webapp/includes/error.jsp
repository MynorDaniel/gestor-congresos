<%
    String error = (String) request.getAttribute("errorAtributo");
    if (error != null) {
%>
    <div class="alert alert-danger text-center rounded-0 mb-0" role="alert">
        <%= error %>
    </div>
<% } %>
