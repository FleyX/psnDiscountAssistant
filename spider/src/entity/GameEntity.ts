class GameEntity {
  id: number = -1;
  serverId: number = 0;
  storeId: string = "";
  name: string = "";
  platform: string = "PS4";
  gameContent: string = "游戏";
  zhName: string = "";
  icon: string = "";
  description: string = "";
  language: string = "";
  publishTime: number = 0;
  type: string = "其他";
  currentPrice: number = 0;
  rmbPrice: number = 0;
  cutPercent: number = 0;
  plusPrice: number = 0;
  rmbPlusPrice: number = 0;
  plusCutPercent: number = 0;
  isPlus: number = 0;
  originPrice: number = 0;
  cutOverTime: number = 0;
  /**
   * 1:是，-1：不是，0:持平
   */
  isLowest: number = 0;
  psnScore: number = 0;
  psnScoreNum: number = 0;
  mediaScore: number = 0;
  createTime: number = 0;
  updateTime: number = 0;
  parentStoreId: string = "";
}

export default GameEntity;
