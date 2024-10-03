import GET_ROUTES from '@_common/getRoutes';
import { Navigate } from 'react-router-dom';
import ROUTES from '@_constants/routes';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { getAccessToken } from '@_utils/tokenManager';

export default function SlashRoute() {
  const token = getAccessToken();
  const lastDarakbangId = getLastDarakbangId();

  if (token) {
    if (lastDarakbangId) {
      return <Navigate to={GET_ROUTES.nowDarakbang.main()} replace />;
    }
    if (!lastDarakbangId) {
      return <Navigate to={ROUTES.darakbangSelectOption} replace />;
    }
  }

  if (!token) {
    return <Navigate to={ROUTES.home} replace />;
  }

  return <Navigate to={ROUTES.home} replace />;
}
