import { getLastDarakbangId } from './lastDarakbangManager';

const getDarakbangIdRoute = (darakbangId: number) =>
  '/darakbang/' + darakbangId;

const GET_ROUTES = {
  nowDarakbangMain: () => getDarakbangIdRoute(getLastDarakbangId() || 0),
};
export default GET_ROUTES;
