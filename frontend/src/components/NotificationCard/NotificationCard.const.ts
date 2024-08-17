import { NotificationType } from '@_types/index';
import { Theme } from '@emotion/react';

export const notificationTypeColors = (
  theme: Theme,
): Record<NotificationType, string> => {
  return {
    MOIM_CREATED: theme.colorPalette.green[300],
    MOIMING_COMPLETED: theme.colorPalette.green[300],
    MOIMING_REOPENED: theme.colorPalette.red[500],
    MOIM_CANCELED: theme.colorPalette.red[500],
    MOIM_MODIFIED: theme.colorPalette.yellow[300],
    NEW_COMMENT: theme.colorPalette.yellow[300],
    NEW_REPLY: theme.colorPalette.yellow[300],
    NEW_CHAT: theme.colorPalette.yellow[300],
    NEW_MOIMEE_JOINED: theme.colorPalette.yellow[300],
    MOIMEE_LEFT: theme.colorPalette.red[500],
    MOIM_PLACE_COMFIRMED: theme.colorPalette.green[300],
    MOIM_TIME_CONFIRMED: theme.colorPalette.green[300],
  };
};
