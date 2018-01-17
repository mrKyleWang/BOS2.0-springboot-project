<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/promotion_detail.css">
<div class="container promotions" >
    <div class="col-md-2 prolist">
        <h5 class="title"><a href="#/promotion"><strong>返回促销列表</strong></a></h5>
        <img src="images/pro.jpg" class="img-responsive">
    </div>
    <div class="col-md-10 procontent">
        <h5 class="title">${promotion.title}</h5>
        <div class="intro">
            <p>活动范围: ${promotion.activeScope} </p>
            <p>活动时间: ${promotion.startDate?string("yyyy-MM-dd")} - ${promotion.endDate?string("yyyy-MM-dd")}</p>
        </div>
        <div class="partline clearfix"></div>
        <div class="promotionbox">
            ${promotion.description}
        </div>
    </div>
</div>
