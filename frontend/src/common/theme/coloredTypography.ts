import { ColoredTypography, Typography } from './theme.type';

import { Entries } from '@_types/index';
import { css } from '@emotion/react';
import typography from './typography';

const coloredTypography: ColoredTypography = (
  Object.entries(typography) as Entries<Typography>
).reduce((object, [key, style]) => {
  object[key] = (fontColor: string) => {
    return css`
      ${style}
      color:${fontColor};
    `;
  };

  return object;
}, {} as ColoredTypography);

export default coloredTypography;
