import { css } from '@emotion/react';
import defaultProfile from '@_common/assets/empty_profile.svg?url';

export const preview = ({
  imageUrl,
  size,
}: {
  imageUrl?: string;
  size?: string;
}) => css`
  flex-shrink: 0;

  width: ${size || '3.5rem'};
  height: ${size || '3.5rem'};

  background: url(${imageUrl}), url(${defaultProfile});
  background-repeat: no-repeat;
  background-position: center center;
  background-size: 100% 100%;
  border-radius: 50%;
  box-shadow: 0 4px 4px 0 #00000040;

  &:active {
    background-color: #868e96;
  }
`;
