import { Navigate, useLocation, useParams } from 'react-router-dom';

import { PropsWithChildren } from 'react';
import ROUTES from '@_constants/routes';
import { checkAuthentication } from '@_utils/checkAuthentication';
import { setLastDarakbangId } from '@_common/lastDarakbangManager';

export default function ProtectedRoute(props: PropsWithChildren) {
  const { children } = props;

  const isAuthenticated = checkAuthentication();
  const location = useLocation();

  const params = useParams();

  const darakbangId = +(params.darakbangId || 0);

  if (darakbangId > 0) {
    setLastDarakbangId(darakbangId);
  }

  return isAuthenticated ? (
    children
  ) : (
    <Navigate to={ROUTES.home} state={{ from: location }} replace />
  );
}
