<%--
  文章正文无sidebar
  User: baitao.jibt@gmail.com
  Date: 12-8-24
  Time: 下午21:19
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>${category.categoryName}</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/ui.totop.css">
    <script src="${ctx}/static/js/jquery.ui.totop.js" type="text/javascript"></script>
</head>
<body>
<!-- 文章导航 -->
<div class="row">
    <div class="span13">
        <ul class="breadcrumb">
            <li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
            <li><a href="${ctx}/article/list/${article.category.id}">${article.category.categoryName}</a> <span class="divider">/</span></li>
            <li class="active">${article.subject}</li>
        </ul>
    </div>
</div>
<div class="row">
    <!-- 正文 -->
    <div class="span13">
        <div class="blog-post">
            <h3>${article.subject}</h3>
            <div class="blog-posted-inner">
                作者: ${article.user.username} &nbsp; | &nbsp; 发表时间: <joda:format value="${article.createdDate}" pattern="yyyy年MM月dd日 hh:mm:ss"/> &nbsp; | &nbsp;
                浏览次数: ${article.views} <c:if test="${article.allowComment}">&nbsp; | &nbsp; 评论数: ${fn:length(article.commentList)}</c:if>
            </div>
            ${article.message}
        </div>
        <c:if test="${not empty relatedArticles}">
            <div id="recentPostList"><!-- relatedPostList -->
                <div id="related-post-title"><h4>相关文章</h4></div>
                <div class="related-item-wrapper">
                    <h4><a href="#">相关文章1</a></h4>
                    <img src="${ctx}/static/images/blog-pic2.jpg" alt="" class="imgleft"/>
                    <!-- some words -->
                </div>
                <div class="related-item-spacer">&nbsp;</div>
                <div class="related-item-wrapper">
                    <h4><a href="#">相关文章2</a></h4>
                    <img src="${ctx}/static/images/blog-pic3.jpg" alt="" class="imgleft"/>
                    <!-- some words -->
                </div>
            </div>
        </c:if>
        <c:if test="${article.allowComment}">
            <div id="disqus_container">
                <a href="#" class="comment btn btn-primary" onclick="return false;">View</a>
                <div id="disqus_thread"></div>
            </div>
            <script type="text/javascript">
                var show_comments = function () {
                    var disqus_shortname = 'sdfie';
                    var disqus_identifier = '${article.id}';
                    /* * * DON'T EDIT BELOW THIS LINE * * */
                    (function () {
                        var dsq = document.createElement('script');
                        dsq.type = 'text/javascript';
                        dsq.async = true;
                        dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';
                        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
                    })();
                };
                $('#disqus_container>.comment').remove();
                show_comments();
                $(document).ready(function () {
                    $('#disqus_container>.comment').click(function () {
                        $(this).html('Loading....');
                        show_comments();
                        $(this).remove();
                    });
                });
            </script>
        </c:if>
    </div>
</div>
<script>
    $(function () {
        //totop
        $().UItoTop({ easingType:'easeOutQuart' });

        //设置图片宽度最大为676px
        $('img').each(function(i){
            //alert($(this).width());
            if($(this).width()>676){
                var b=676/($(this).width());
                $(this).width(676);
                $(this).height($(this).height()*b);
            }
        });
    });
</script>
</body>
</html>