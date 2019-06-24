import path from 'path';

/**
 * 使用请先设置根目录
 */
class PathUtil {
  static rootPath: string = path.resolve(__dirname, '../../');

  /**
   *  获取绝对路径
   *
   * @param partPath 相对路径
   */
  public static getAbsPath(partPath: string): string {
    return path.resolve(PathUtil.rootPath, partPath);
  }
}

export { PathUtil, path };
