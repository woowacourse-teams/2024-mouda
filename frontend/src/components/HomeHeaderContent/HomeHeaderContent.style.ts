import { Theme, css } from '@emotion/react';

import { common } from '@_common/common.style';

export const logoStyle = (props: { theme: Theme }) => css`
  ${props.theme.typography.h5}
  ${common.nonScroll}
`;
