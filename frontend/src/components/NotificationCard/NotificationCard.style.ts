import { theme } from '@_common/theme/theme.style';
import { css, Theme } from '@emotion/react';

export const CardBox = (props: { theme: Theme }) => css`
  display: flex;
  gap: 1rem;
  align-items: center;
  width: 100%;
  border-radius: 1rem;
  &:active {
    background-color: ${props.theme.colorPalette.grey[100]};
  }
`;

export const TextInfoBox = css`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

export const colorDot = (props: { theme: Theme; typeColor: string }) => css`
  color: ${props.typeColor};
  ${theme.typography.h1}
`;

export const Title = (props: { theme: Theme }) => css`
  ${props.theme.typography.b1}
`;
export const SubTitle = (props: { theme: Theme }) => css`
  color: ${props.theme.colorPalette.grey[400]};
  ${props.theme.typography.b3}
`;
