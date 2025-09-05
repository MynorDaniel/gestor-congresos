<%
    String error = (String) request.getAttribute("errorAtributo");
    if (error != null) {
%>
    <div class="alert alert-danger text-center mt-2" role="alert">
        <%= error %>
    </div>
<% } %>
