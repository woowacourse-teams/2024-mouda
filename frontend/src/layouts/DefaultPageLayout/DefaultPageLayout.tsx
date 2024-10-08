import { PropsWithChildren } from 'react';
import { useTheme } from '@emotion/react';
import FixedCreateButtonWrapper from '@_layouts/components/FixedCreationButtonWrapper/FixedCreateButtonWrapper';
import DefaultPageMain from './DefaultPageMain/DefaultPageMain';
import * as S from './DefaultpageLayout.style';
import DoubleTriHeader from '@_layouts/components/DoubleTriHeader/DoubleTriHeader';
import TriSectionHeader from '@_layouts/components/TriSectionHeader/TriSectionHeader';

function DefaultPageLayout(props: PropsWithChildren) {
  const { children } = props;

  const theme = useTheme();

  return <div css={S.containerStyle({ theme })}>{children}</div>;
}

DefaultPageLayout.DoubleTriHeader = DoubleTriHeader;
DefaultPageLayout.TriHeader = TriSectionHeader;
DefaultPageLayout.Main = DefaultPageMain;
DefaultPageLayout.ListPageFixedButtonWrapper = FixedCreateButtonWrapper;

export default DefaultPageLayout;
