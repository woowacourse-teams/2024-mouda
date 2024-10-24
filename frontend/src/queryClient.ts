import * as Sentry from '@sentry/react';

import { QueryCache, QueryClient } from '@tanstack/react-query';

import { ApiError } from '@_utils/customError/ApiError';
import { removeToken } from '@_utils/tokenManager';
import ROUTES from '@_constants/routes';
import GET_ROUTES from '@_common/getRoutes';

const createQueryClient = () => {
  return new QueryClient({
    defaultOptions: {
      queries: {
        networkMode: 'always',
        retry: (failureCount, error) => {
          if (error instanceof ApiError && error.status === 401) {
            return false;
          }
          return failureCount < 3;
        },
      },
      mutations: {
        onError: (error) => handleApiError(error),
        networkMode: 'always',
      },
    },
    queryCache: new QueryCache({
      onError: (error) => handleApiError(error),
    }),
  });
};

const handleApiError = (error: Error) => {
  Sentry.captureException(error);
  console.log(error);
  if (error instanceof ApiError) {
    if (error.status === 401) {
      removeToken();
      window.location.href = ROUTES.home;
      return;
    }
    if (
      error.message === '다락방이 존재하지 않습니다.' ||
      error.message === '가입한 다락방이 아닙니다.'
    ) {
      window.location.href = ROUTES.darakbangSelectOption;
      return;
    }
    if (error.message === '모임이 존재하지 않습니다.') {
      window.location.href = GET_ROUTES.nowDarakbang.main();
      return;
    }
  } else {
    alert(error instanceof Error ? error.message : 'An error occurred');
  }
};

export default createQueryClient;
