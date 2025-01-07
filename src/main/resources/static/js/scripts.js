// 简单的表单验证逻辑
document.querySelector('form').onsubmit = function () {
    alert('Form submitted!');
};

    // 3秒后自动隐藏提示框
    setTimeout(function () {
    document.querySelector('.alert').style.display = 'none';
}, 3000);

