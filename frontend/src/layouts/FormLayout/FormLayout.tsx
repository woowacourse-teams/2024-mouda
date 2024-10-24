import * as S from './FormLayout.style';

import FormBottomWrapper from './FormBottomWrapper/FormBottomButtonWrapper';
import FormHeader from './FormHeader/FormHeader';
import FormMain from './FormMain/FormMain';
import { PropsWithChildren } from 'react';

function FormLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.containerStyle}>{children}</div>;
}

FormLayout.Header = FormHeader;
FormLayout.MainForm = FormMain;
FormLayout.BottomButtonWrapper = FormBottomWrapper;

export default FormLayout;
