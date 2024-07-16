import { PropsWithChildren } from 'react';
import * as S from './FormLayout.style';
import FormHeader from './FormHeader/FormHeader';
import FormMain from './FormMain/FormMain';
import FormBottomButtonWrapper from './FormBottomButtonWrapper/FormBottomButtonWrapper';

function FormLayout(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.containerStyle}>{children}</div>;
}

FormLayout.Header = FormHeader;
FormLayout.MainForm = FormMain;
FormLayout.BottomButtonWrapper = FormBottomButtonWrapper;

export default FormLayout;
