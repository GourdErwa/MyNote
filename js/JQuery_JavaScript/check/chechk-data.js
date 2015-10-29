/**
 * 数据校验
 *
 * Created by wei.Li on 14/11/21.
 */


//TODO
//FIXME
var checkArgs = new function () {

    var _toString = Object.prototype.toString;
    //默认文本框最大长度
    var defaultMaxLength = 50;

    /**
     * 校验数据是否为空，不保证每个元素的值不为空
     *
     * @param {string/number/object} args
     * @returns {boolean} null / undefined / {} / [] / NaN / '' 默认返回 true(是空值)
     */
    this.checkIsEmpty = function (args) {

        var type = _toString.call(args).slice(8, -1);

        switch (type) {
            case 'String':
                return !$.trim(args);
            case 'Array':
                return !args.length;
            case 'Object':
                // 普通对象使用 for...in 判断，有 key 即为 false
                if (!args) return true;
                for (var key in args)
                    return false;
                return true;
            default:
                // 其他对象均视作空
                return true;
        }
    };

    /**
     * 校验数据是否为空着，不保证每个元素的值不为空
     *
     * @param {string/number/object} args
     * @returns {boolean} is undefined OR  NaN  默认返回 false
     */
    this.checkIsBlank = function (args) {

        return (typeof args !== 'undefined' && (args == null || args == '' || args.length == 0) );

    };


    /* */
    /**
     * @deprecated
     * 校验数据中是否 ，保证每个元素的值不为空
     *
     * @param {string/number/object} args
     * @returns {boolean} null / undefined / {} / [] / NaN / '' 默认返回 true(是空值)
     */
    /*
     this.checkObjHaveValIsEmpty = function (args) {

     var type = Object.prototype.toString.call(args).slice(8, -1);
     switch (type) {
     case 'String':
     return !$.trim(args);
     case 'Array':
     return this.checkObjsHaveEmptyElement(args);
     case 'Object':
     return this.checkObjsHaveEmptyElement(args);
     default:
     // 其他对象均视作非空
     return true;
     }
     };*/

    /**
     * 校验参数中每个元素是否含有 空值
     * @param {object}[args]
     * @returns {boolean} 有某个元素含有空值返回 true
     */
    this.checkObjsHaveEmptyElement = function (args) {

        //checkIsEmpty 方法保证对象数据不为空，当不保证每个元素的值不为空
        if (this.checkIsEmpty(args)) {
            return true;
        }

        //校验每个值不为空
        for (var key in args) {
            var obj = args[key];
            if (obj == '') return true;
            for (var element in obj) {
                if ($.trim(obj[element]) == '') {
                    return true;
                }
            }
        }
        return false;
    };


    /**
     * 校验参数 是否为空对象 或者 数据每个元素是否含有空值
     * @param {object}[args]
     * @returns {boolean} 有某个元素含有空值返回 true
     */
    this.checkObjsIsBlankOrHaveEmptyElement = function (args) {

        if (this.checkIsBlank(args)) return false;

        return this.checkObjsHaveEmptyElement(args);
    };

    /**
     * 校验 arg 是否在 JSONObject 中
     * @param {Object} [JSONObject]    JSONObject
     * @param {Object} arg
     * @returns {boolean}  存在 true(arg是JSONObject的一个元素)
     */
    this.isElementInJSONObject = function (JSONObject, arg) {

        if (!checkArgs.isTypeOfObject([JSONObject, arg])) {
            return false;
        }

        var b = false;
        $.each(JSONObject, function () {
            if (JSON.stringify(arg) == JSON.stringify(this)) {
                b = true;
                return false;
            }
        });
        return b;
    };

    /**
     * @param  {object} [arg]   对象数据
     * @returns {boolean} 如果有一个类型不为 object 则返回 false
     */
    this.isTypeOfObject = function (arg) {

        if (typeof arg === 'object') {

            var b = true;
            $.each(arg, function () {

                var type = _toString.call(this).slice(8, -1);
                if (type !== 'Object') {
                    b = false;
                    return false;
                }
            });
            return b;
        }
        return false;
    };

    /**
     * 是否匹配 email 格式
     * @param arg 参数
     * @returns {boolean} 是 Email 格式返回 true
     */
    this.checkEmail = function (arg) {
        return (arg.search(/^\w+((-\w+)|(\.\w+))*@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
    };


    /**
     * 是否是数字
     * @param {string} arg
     * @returns {boolean} 是数字返回 true
     */
    this.checkNumber = function (arg) {
        return $.isNumeric(arg);
    };


    /**
     * 是否是数字 且长度为 < defaultMaxLength
     * @param {number} arg
     * @returns {boolean}
     */
    this.checkNumberLtDefaultMaxLength = function (arg) {
        return $.isNumeric(arg) && arg.toString().length < defaultMaxLength;
    };


    /**
     * 是否是数字,且最大长度为 < maxLength
     * @param  {number} arg
     * @param  {number} maxLength
     * @returns {boolean}
     */
    this.checkNumberLtMaxLength = function (arg, maxLength) {
        return $.isNumeric(arg) && arg.toString().length < maxLength;
    };

};
