import GET_ROUTES from '@_common/getRoutes';
import { Navigate } from 'react-router-dom';
import ROUTES from '@_constants/routes';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { getToken } from '@_utils/tokenManager';

export default function SlashRoute() {
  const token = getToken();
  const lastDarakbangId = getLastDarakbangId();

  if (token) {
    if (lastDarakbangId) {
      return <Navigate to={GET_ROUTES.nowDarakbang.main()} />;
    }
    if (!lastDarakbangId) {
      return <Navigate to={ROUTES.darakbangSelectOption} />;
    }
  }

  if (!token) {
    return <Navigate to={ROUTES.home} />;
  }

  return <Navigate to={ROUTES.home} />;
}
