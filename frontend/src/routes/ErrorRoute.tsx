import { Navigate, useRouteError } from 'react-router-dom';

import { ApiError } from '@_utils/customError/ApiError';
import GET_ROUTES from '@_common/getRoutes';
import ROUTES from '@_constants/routes';

export default function ErrorRoute() {
  const error = useRouteError();
  if (error instanceof ApiError) {
    switch (error.message) {
      case '다락방이 존재 하지 않습니다.':
        return <Navigate to={ROUTES.darakbangSelectOption} />;
      case '모임이 존재하지 않습니다':
        return <Navigate to={ROUTES.darakbangSelectOption} />;
      case '모임이 존재하지 않습니다.':
        return <Navigate to={GET_ROUTES.nowDarakbang.main()} />;
      default:
        throw error;
    }
  }
  throw error;
}
