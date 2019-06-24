let chars = [
  [
    '0',
    '1',
    '2',
    '3',
    '4',
    '5',
    '6',
    '7',
    '8',
    '9',
    'a',
    'b',
    'c',
    'd',
    'e',
    'f',
    'g',
    'h',
    'i',
    'j',
    'k',
    'm',
    'n',
    'o',
    'p',
    'q',
    'r',
    's',
    't',
    'u',
    'v',
    'w',
    'x',
    'y',
    'z'
  ],
  [
    'a',
    'b',
    'c',
    'd',
    'e',
    'f',
    'g',
    'h',
    'i',
    'j',
    'k',
    'm',
    'n',
    'o',
    'p',
    'q',
    'r',
    's',
    't',
    'u',
    'v',
    'w',
    'x',
    'y',
    'z'
  ],
  ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
];

class StringHelper {
  /**
   * 获取随机字符串
   * @param {*0：字母数字混合 1：字母 2：数字} mode
   * @param {*字符串长度} length
   */
  static getRandomString(mode = 0, length = 6): string {
    let temp = chars[mode];
    let max = temp.length - 1;
    let str = '';
    for (let i = 0; i < length; i++) {
      str += temp[StringHelper.getRandomNumber(max, 0)];
    }
    return str;
  }

  /**
   * 返回指定范围一个随机数
   * @param max 最大值
   * @param min 最小值
   */
  static getRandomNumber(max: number, min: number): number {
    var Range = max - min;
    var Rand = Math.random();
    return min + Math.round(Rand * Range);
  }

  /**
   * 判断一个字符串是否为空
   * @param str 待判断参数
   */
  static isEmpty(str: string): boolean {
    return str == null || str.trim().length == 0;
  }
}

export default StringHelper;
