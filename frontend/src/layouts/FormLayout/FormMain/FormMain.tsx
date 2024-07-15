import { PropsWithChildren } from 'react';
import * as S from './FormMain.style';

export default function FormMain(props: PropsWithChildren) {
  const { children } = props;

  return <form css={S.formStyle}>{children}</form>;
}
