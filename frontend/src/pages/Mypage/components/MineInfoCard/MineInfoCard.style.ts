import { css, Theme } from '@emotion/react';

export const MineInfoContainer = (props: { theme: Theme }) => css`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
  justify-content: flex-end;

  width: 100%;
  height: 30vh;

  background: linear-gradient(
    ${props.theme.colorPalette.grey[200]} 62%,
    white 38%
  );
`;
export const MinetextWrapper = () => css`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  align-items: flex-end;
`;

export const profileWrapper = () => css`
  position: relative;
`;

export const editSVG = () => css`
  position: absolute;
  inset: auto 5px 10px auto;
  width: 20px;
  height: 20px;
`;

export const input = (props: { theme: Theme }) => css`
  ${props.theme.typography.h5}
  max-width: 60%;
`;
