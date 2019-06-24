import ListApi from './ListApi';

class ServerEntity {
  serverId: number;
  name: string;
  exchangeRate: number;
  moneySymbol: string;
  gameDetailApi: string;
  listApi: Array<ListApi>;
}

export default ServerEntity;
