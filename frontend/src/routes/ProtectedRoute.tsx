import ROUTES from '@_constants/routes';
import { checkAuthentication } from '@_utils/checkAuthentication';
import { PropsWithChildren } from 'react';
import { Navigate, useLocation } from 'react-router-dom';

export default function ProtectedRoute(props: PropsWithChildren) {
  const { children } = props;

  const isAuthenticated = checkAuthentication();
  const location = useLocation();

  return isAuthenticated ? (
    children
  ) : (
    <Navigate to={ROUTES.home} state={{ from: location }} replace />
  );
}
