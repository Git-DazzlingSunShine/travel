/// 获取验证码图片
function changeCaptcha(img) {
    img.src = "captcha_img?date=" + new Date().getTime();
}
