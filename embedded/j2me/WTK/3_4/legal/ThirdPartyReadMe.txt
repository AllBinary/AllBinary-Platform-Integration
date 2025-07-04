DO NOT TRANSLATE OR LOCALIZE

***************************************************************************

%%The following software may be included in this product:
JPEG

Use of any of this software is governed by the terms of the license below:

Taken from code......

LEGAL ISSUES
============

In plain English:

1. We don't promise that this software works.  (But if you find any bugs,
   please let us know!)
2. You can use this software for whatever you want.  You don't have to pay us.
3. You may not pretend that you wrote this software.  If you use it in a
   program, you must acknowledge somewhere in your documentation that
   you've used the IJG code.

In legalese:

The authors make NO WARRANTY or representation, either express or implied,
with respect to this software, its quality, accuracy, merchantability, or
fitness for a particular purpose.  This software is provided "AS IS", and you,
its user, assume the entire risk as to its quality and accuracy.

This software is copyright (C) 1991-1998, Thomas G. Lane.
All Rights Reserved except as specified below.

Permission is hereby granted to use, copy, modify, and distribute this
software (or portions thereof) for any purpose, without fee, subject to these
conditions:
(1) If any part of the source code for this software is distributed, then this
README file must be included, with this copyright and no-warranty notice
unaltered; and any additions, deletions, or changes to the original files
must be clearly indicated in accompanying documentation.
(2) If only executable code is distributed, then the accompanying
documentation must state that "this software is based in part on the work of
the Independent JPEG Group".
(3) Permission for use of this software is granted only if the user accepts
full responsibility for any undesirable consequences; the authors accept
NO LIABILITY for damages of any kind.

These conditions apply to any software derived from or based on the IJG code,
not just to the unmodified library.  If you use our work, you ought to
acknowledge us.

Permission is NOT granted for the use of any IJG author's name or company name
in advertising or publicity relating to this software or products derived from
it.  This software may be referred to only as "the Independent JPEG Group's
software".

We specifically permit and encourage the use of this software as the basis of
commercial products, provided that all warranty or liability claims are
assumed by the product vendor.


ansi2knr.c is included in this distribution by permission of L. Peter Deutsch,
sole proprietor of its copyright holder, Aladdin Enterprises of Menlo Park, CA.
ansi2knr.c is NOT covered by the above copyright and conditions, but instead
by the usual distribution terms of the Free Software Foundation; principally,
that you must include source code if you redistribute it.  (See the file
ansi2knr.c for full details.)  However, since ansi2knr.c is not needed as part
of any program generated from the IJG code, this does not limit you more than
the foregoing paragraphs do.

The Unix configuration script "configure" was produced with GNU Autoconf.
It is copyright by the Free Software Foundation but is freely distributable.
The same holds for its supporting scripts (config.guess, config.sub,
ltconfig, ltmain.sh).  Another support script, install-sh, is copyright
by M.I.T. but is also freely distributable.

It appears that the arithmetic coding option of the JPEG spec is covered by
patents owned by IBM, AT&T, and Mitsubishi.  Hence arithmetic coding cannot
legally be used without obtaining one or more licenses.  For this reason,
support for arithmetic coding has been removed from the free JPEG software.
(Since arithmetic coding provides only a marginal gain over the unpatented
Huffman mode, it is unlikely that very many implementations will support it.)
So far as we are aware, there are no patent restrictions on the remaining
code.

The IJG distribution formerly included code to read and write GIF files.
To avoid entanglement with the Unisys LZW patent, GIF reading support has
been removed altogether, and the GIF writer has been simplified to produce
"uncompressed GIFs".  This technique does not use the LZW algorithm; the
resulting GIF files are larger than usual, but are readable by all standard
GIF decoders.

We are required to state that
    "The Graphics Interchange Format(c) is the Copyright property of
    CompuServe Incorporated.  GIF(sm) is a Service Mark property of
    CompuServe Incorporated."

Additional License(s)

"copyright"


***************************************************************************

%%The following software may be included in this product:
Apache log4j

Use of any of this software is governed by the terms of the license below:

Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/

   TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION

   1. Definitions.

      "License" shall mean the terms and conditions for use, reproduction,
      and distribution as defined by Sections 1 through 9 of this document.

      "Licensor" shall mean the copyright owner or entity authorized by
      the copyright owner that is granting the License.

      "Legal Entity" shall mean the union of the acting entity and all
      other entities that control, are controlled by, or are under common
      control with that entity. For the purposes of this definition,
      "control" means (i) the power, direct or indirect, to cause the
      direction or management of such entity, whether by contract or
      otherwise, or (ii) ownership of fifty percent (50%) or more of the
      outstanding shares, or (iii) beneficial ownership of such entity.

      "You" (or "Your") shall mean an individual or Legal Entity
      exercising permissions granted by this License.

      "Source" form shall mean the preferred form for making modifications,
      including but not limited to software source code, documentation
      source, and configuration files.

      "Object" form shall mean any form resulting from mechanical
      transformation or translation of a Source form, including but
      not limited to compiled object code, generated documentation,
      and conversions to other media types.

      "Work" shall mean the work of authorship, whether in Source or
      Object form, made available under the License, as indicated by a
      copyright notice that is included in or attached to the work
      (an example is provided in the Appendix below).

      "Derivative Works" shall mean any work, whether in Source or Object
      form, that is based on (or derived from) the Work and for which the
      editorial revisions, annotations, elaborations, or other modifications
      represent, as a whole, an original work of authorship. For the purposes
      of this License, Derivative Works shall not include works that remain
      separable from, or merely link (or bind by name) to the interfaces of,
      the Work and Derivative Works thereof.

      "Contribution" shall mean any work of authorship, including
      the original version of the Work and any modifications or additions
      to that Work or Derivative Works thereof, that is intentionally
      submitted to Licensor for inclusion in the Work by the copyright owner
      or by an individual or Legal Entity authorized to submit on behalf of
      the copyright owner. For the purposes of this definition, "submitted"
      means any form of electronic, verbal, or written communication sent
      to the Licensor or its representatives, including but not limited to
      communication on electronic mailing lists, source code control systems,
      and issue tracking systems that are managed by, or on behalf of, the
      Licensor for the purpose of discussing and improving the Work, but
      excluding communication that is conspicuously marked or otherwise
      designated in writing by the copyright owner as "Not a Contribution."

      "Contributor" shall mean Licensor and any individual or Legal Entity
      on behalf of whom a Contribution has been received by Licensor and
      subsequently incorporated within the Work.

   2. Grant of Copyright License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      copyright license to reproduce, prepare Derivative Works of,
      publicly display, publicly perform, sublicense, and distribute the
      Work and such Derivative Works in Source or Object form.

   3. Grant of Patent License. Subject to the terms and conditions of
      this License, each Contributor hereby grants to You a perpetual,
      worldwide, non-exclusive, no-charge, royalty-free, irrevocable
      (except as stated in this section) patent license to make, have made,
      use, offer to sell, sell, import, and otherwise transfer the Work,
      where such license applies only to those patent claims licensable
      by such Contributor that are necessarily infringed by their
      Contribution(s) alone or by combination of their Contribution(s)
      with the Work to which such Contribution(s) was submitted. If You
      institute patent litigation against any entity (including a
      cross-claim or counterclaim in a lawsuit) alleging that the Work
      or a Contribution incorporated within the Work constitutes direct
      or contributory patent infringement, then any patent licenses
      granted to You under this License for that Work shall terminate
      as of the date such litigation is filed.

   4. Redistribution. You may reproduce and distribute copies of the
      Work or Derivative Works thereof in any medium, with or without
      modifications, and in Source or Object form, provided that You
      meet the following conditions:

      (a) You must give any other recipients of the Work or
          Derivative Works a copy of this License; and

      (b) You must cause any modified files to carry prominent notices
          stating that You changed the files; and

      (c) You must retain, in the Source form of any Derivative Works
          that You distribute, all copyright, patent, trademark, and
          attribution notices from the Source form of the Work,
          excluding those notices that do not pertain to any part of
          the Derivative Works; and

      (d) If the Work includes a "NOTICE" text file as part of its
          distribution, then any Derivative Works that You distribute must
          include a readable copy of the attribution notices contained
          within such NOTICE file, excluding those notices that do not
          pertain to any part of the Derivative Works, in at least one
          of the following places: within a NOTICE text file distributed
          as part of the Derivative Works; within the Source form or
          documentation, if provided along with the Derivative Works; or,
          within a display generated by the Derivative Works, if and
          wherever such third-party notices normally appear. The contents
          of the NOTICE file are for informational purposes only and
          do not modify the License. You may add Your own attribution
          notices within Derivative Works that You distribute, alongside
          or as an addendum to the NOTICE text from the Work, provided
          that such additional attribution notices cannot be construed
          as modifying the License.

      You may add Your own copyright statement to Your modifications and
      may provide additional or different license terms and conditions
      for use, reproduction, or distribution of Your modifications, or
      for any such Derivative Works as a whole, provided Your use,
      reproduction, and distribution of the Work otherwise complies with
      the conditions stated in this License.

   5. Submission of Contributions. Unless You explicitly state otherwise,
      any Contribution intentionally submitted for inclusion in the Work
      by You to the Licensor shall be under the terms and conditions of
      this License, without any additional terms or conditions.
      Notwithstanding the above, nothing herein shall supersede or modify
      the terms of any separate license agreement you may have executed
      with Licensor regarding such Contributions.

   6. Trademarks. This License does not grant permission to use the trade
      names, trademarks, service marks, or product names of the Licensor,
      except as required for reasonable and customary use in describing the
      origin of the Work and reproducing the content of the NOTICE file.

   7. Disclaimer of Warranty. Unless required by applicable law or
      agreed to in writing, Licensor provides the Work (and each
      Contributor provides its Contributions) on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
      implied, including, without limitation, any warranties or conditions
      of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A
      PARTICULAR PURPOSE. You are solely responsible for determining the
      appropriateness of using or redistributing the Work and assume any
      risks associated with Your exercise of permissions under this License.

   8. Limitation of Liability. In no event and under no legal theory,
      whether in tort (including negligence), contract, or otherwise,
      unless required by applicable law (such as deliberate and grossly
      negligent acts) or agreed to in writing, shall any Contributor be
      liable to You for damages, including any direct, indirect, special,
      incidental, or consequential damages of any character arising as a
      result of this License or out of the use or inability to use the
      Work (including but not limited to damages for loss of goodwill,
      work stoppage, computer failure or malfunction, or any and all
      other commercial damages or losses), even if such Contributor
      has been advised of the possibility of such damages.

   9. Accepting Warranty or Additional Liability. While redistributing
      the Work or Derivative Works thereof, You may choose to offer,
      and charge a fee for, acceptance of support, warranty, indemnity,
      or other liability obligations and/or rights consistent with this
      License. However, in accepting such obligations, You may act only
      on Your own behalf and on Your sole responsibility, not on behalf
      of any other Contributor, and only if You agree to indemnify,
      defend, and hold each Contributor harmless for any liability
      incurred by, or claims asserted against, such Contributor by reason
      of your accepting any such warranty or additional liability.

   END OF TERMS AND CONDITIONS

   APPENDIX: How to apply the Apache License to your work.

      To apply the Apache License to your work, attach the following
      boilerplate notice, with the fields enclosed by brackets "[]"
      replaced with your own identifying information. (Don't include
      the brackets!)  The text should be enclosed in the appropriate
      comment syntax for the file format. We also recommend that a
      file or class name and description of purpose be included on the
      same "printed page" as the copyright notice for easier
      identification within third-party archives.

   Copyright [yyyy] [name of copyright owner]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

***************************************************************************

%%The following software may be included in this product:
OpenGL ES Header Files

Use of any of this software is governed by the terms of the license below:

** License Applicability. Except to the extent portions of this file are
** made subject to an alternative license as permitted in the SGI Free
** Software License B, Version 1.0 (the "License"), the contents of this
** file are subject only to the provisions of the License. You may not use
** this file except in compliance with the License. You may obtain a copy
** of the License at Silicon Graphics, Inc., attn: Legal Services, 1600
** Amphitheatre Parkway, Mountain View, CA 94043-1351, or at:
** 
** http://oss.sgi.com/projects/FreeB
** 
** Note that, as provided in the License, the Software is distributed on an
** "AS IS" basis, with ALL EXPRESS AND IMPLIED WARRANTIES AND CONDITIONS
** DISCLAIMED, INCLUDING, WITHOUT LIMITATION, ANY IMPLIED WARRANTIES AND
** CONDITIONS OF MERCHANTABILITY, SATISFACTORY QUALITY, FITNESS FOR A
** PARTICULAR PURPOSE, AND NON-INFRINGEMENT.
** 
** Original Code. The Original Code is: OpenGL Sample Implementation,
** Version 1.2.1, released January 26, 2000, developed by Silicon Graphics,
** Inc. The Original Code is Copyright (c) 1991-2000 Silicon Graphics, Inc.
** Copyright in any portions created by third parties is as indicated
** elsewhere herein. All Rights Reserved.
** 
** Additional Notice Provisions: The application programming interfaces
** established by SGI in conjunction with the Original Code are The
** OpenGL(R) Graphics System: A Specification (Version 1.2.1), released
** April 1, 1999; The OpenGL(R) Graphics System Utility Library (Version
** 1.3), released November 4, 1998; and OpenGL(R) Graphics with the X
** Window System(R) (Version 1.3), released October 19, 1998. This software
** was created using the OpenGL(R) version 1.2.1 Sample Implementation
** published by SGI, but has not been independently verified as being
** compliant with the OpenGL(R) version 1.2.1 Specification.


***************************************************************************

%%The following software may be included in this product:
Microsoft.VC80.CRT.manifest from Microsoft C++

Use of any of this software is governed by the terms of the license below:

END-USER LICENSE AGREEMENT FOR MICROSOFT SOFTWARE

IMPORTANT-READ CAREFULLY: This Microsoft End-User License Agreement (''EULA'')
is a legal agreement between you (either an individual or a single entity) and
Microsoft Corporation for the Microsoft software product(s) accompanying this
EULA, which include(s) computer software and may include "online" or electronic
documentation, associated media, and printed materials (''SOFTWARE PRODUCT'').
By installing, copying, or otherwise using the SOFTWARE PRODUCT or any UPDATES
(as defined below), you agree to be bound by the terms of this EULA. If you do
not agree to the terms of this EULA, do not install, copy, or otherwise use the
SOFTWARE PRODUCT; you may, however, return it to your place of purchase for a
full refund. In addition, by installing, copying, or otherwise using any updates
or other components of the SOFTWARE PRODUCT that you receive separately as part
of the SOFTWARE PRODUCT (''UPDATES''), you agree to be bound by any additional
license terms that accompany such UPDATES. If you do not agree to the additional
license terms that accompany such UPDATES, you may not install, copy, or
otherwise use such UPDATES.

SOFTWARE PRODUCT LICENSE
The SOFTWARE PRODUCT is protected by copyright laws and international copyright
treaties, as well as other intellectual property laws and treaties. The SOFTWARE
PRODUCT is licensed, not sold. NOTE: The terms of a printed, paper EULA which
may accompany the SOFTWARE PRODUCT supersede the terms of any on-screen EULA
found within the SOFTWARE PRODUCT.

1.	LICENSE TO USE SOFTWARE PRODUCT. 
1.1	General License Grant. Microsoft grants to you as an individual, a personal,
nonexclusive license to make and use copies of the SOFTWARE PRODUCT for the sole
purposes of designing, developing, and testing your software product(s) that are
designed to operate in conjunction with any Microsoft operating system product.
You may install copies of the SOFTWARE PRODUCT on an unlimited number of
computers provided that you are the only individual using the SOFTWARE PRODUCT.
If you are an entity, Microsoft grants you the right to designate one individual
within your organization to have the sole right to use the SOFTWARE PRODUCT in
the manner provided above.
1.2	Documentation. This EULA grants you, as an individual, a personal,
nonexclusive license to make and use an unlimited number of copies of any
documentation, provided that such copies shall be used only for personal
purposes and are not to be republished or distributed (either in hard copy or
electronic form) beyond the user's premises and with the following exception:
you may use documentation identified in the MSDN Library portion of the SOFTWARE
PRODUCT as the file format specification for Microsoft Word, Microsoft Excel,
Microsoft Access, and/or Microsoft PowerPoint ("File Format Documentation")
solely in connection with your development of software product(s) that operate
in conjunction with Windows or Windows NT that are not general purpose word
processing, spreadsheet, or database management software products or an
integrated work or product suite whose components include one or more general
purpose word processing, spreadsheet, or database management software products.
Note: A product that includes limited word processing, spreadsheet, or database
components along with other components that provide significant and primary
value, such as an accounting product with limited spreadsheet capability, is not
considered to be a "general purpose" product.
1.3	Storage/Network Use. You may also store or install a copy of the SOFTWARE
PRODUCT on a storage device, such as a network server, used only to install or
run the SOFTWARE PRODUCT on computers used by a licensed end user in accordance
with Section 1.1.  A single license for the SOFTWARE PRODUCT may not be shared
or used concurrently by other end users.
1.4	Visual Studio-Effect of EULA. This Section 1.4 also applies if the SOFTWARE
PRODUCT is Microsoft Visual Studio, a suite of development tools and other
software programs (each such tool or software program, a "Component").
Components that you receive as part of the SOFTWARE PRODUCT may include a
separate end-user license agreement (each, a "Component EULA"). Except as
provided in Section 6, in the event of inconsistencies between this EULA and any
Component EULA, the terms of this EULA shall control.
1.5	Microsoft Internet Explorer. You may make and use copies of the Microsoft
Internet Explorer for use on all computers for which you have a validly licensed
copy of Microsoft operating system products. 

2.	REDISTRIBUTABLE CODE-ADDITIONAL LICENSE RIGHTS. In addition to the rights
granted in Section 1, certain portions of the SOFTWARE PRODUCT, as described in
this Section 2, are provided to you with additional license rights provided that
you comply with the terms of 
Section 3.1.
2.1	Sample Code. Microsoft grants you the right to use and modify the source
code version of those portions of the SOFTWARE PRODUCT identified as "Samples"
in REDIST.TXT or elsewhere in the SOFTWARE PRODUCT ("Sample Code") for the sole
purposes of designing, developing, and testing your software product(s), and to
reproduce and distribute the Sample Code, along with any modifications thereof,
only in object code form.
2.2	Redistributable Code-Standard. Microsoft grants you a nonexclusive,
royalty-free right to reproduce and distribute the object code form of any
portion of the SOFTWARE PRODUCT listed in REDIST.TXT ("Redistributable Code").
NOTE: certain Redistributable Code may be subject to the restrictions in Section
2.3 if it is also identified as "Limited Use Redistributable Code."
2.3	Redistributable Code-Limited Use. Provided that you ALSO comply with the
terms of Section 3.1.3, Microsoft grants you a nonexclusive, royalty-free right
to reproduce and distribute the object code form of those portions of the
SOFTWARE PRODUCT listed in REDIST.TXT as Limited Use Redistributable Code
("Limited Use Redistributable Code").
2.4	Visual C++ and Visual Studio-Microsoft Foundation Classes (MFC), Template
Libraries (ATL), and C runtimes (CRTs). If this EULA accompanies Visual C++ or
Visual Studio, then in addition to the rights granted in Section 1, Microsoft
grants you the right to use and modify the source code version of those portions
of the SOFTWARE PRODUCT that are identified as MFC, ATL, or CRTs (collectively,
the "VC Redistributables"), for the sole purposes of designing, developing, and
testing your software product(s). Provided you comply with Section 3.1 and you
rename any files created by you that are included in the Licensed Product
(defined below), Microsoft grants you a nonexclusive, royalty-free right to
reproduce and distribute the object code version of the VC Redistributables,
including any modifications you make. For purposes of this section,
"modifications" shall mean enhancements to the functionality of the VC
Redistributables.

3.	DISTRIBUTION REQUIREMENTS; LICENSE RESTRICTIONS. 
3.1	General. The SOFTWARE PRODUCT may contain up to three categories of
redistributable code, any redistribution of which by you requires compliance
with the following terms. 
3.1.1.	Redistributable Code-Standard. If you are authorized and choose to
redistribute Sample Code, Redistributable Code, and Limited Use Redistributable
Code, (collectively, the "Redistributables") as described in Section 2, you
agree to: (a) distribute the Redistributables in object code form and only in
conjunction with and as a part of a software application product developed by
you using the product accompanying this EULA that adds significant and primary
functionality to the SOFTWARE PRODUCT ("Licensed Product"); (b) not use
Microsoft's name, logo, or trademarks to market the Licensed Product; (c)
include a valid copyright notice on the Licensed Product; (d) indemnify, hold
harmless, and defend Microsoft from and against any claims or lawsuits,
including attorney's fees, that arise or result from the use or distribution of
the Licensed Product; (e) include "Copyright  Microsoft Systems Journal"
in all Microsoft Systems Journal (MSJ) code used within your program(s); (f)
otherwise comply with the terms of this EULA; and (g) agree that Microsoft
reserves all rights not expressly granted. You also agree not to permit further
distribution of the Redistributables by your end users except: (1) you may
permit further redistribution of the Redistributables by your distributors to
your end-user customers if your distributors only distribute the
Redistributables in conjunction with, and as part of, the Licensed Product and
you and your distributors comply with all other terms of this EULA; and (2) in
the manner described in Section 3.1.2. 
3.1.2	Redistributable Code-Extended Use. Visual Basic, Visual C++, Visual J++,
and Visual Studio. If this EULA accompanies any of the Microsoft products listed
in the heading of this subsection, and subject to your compliance with Section
3.1.1, you may permit your end users to reproduce and distribute the object code
form of certain portions of the SOFTWARE PRODUCT (as listed in REDIST.TXT as
"Extended Use Redistributable Code") only in conjunction with and part of a
Licensed Product and/or Web page that adds significant and primary functionality
to the Extended Use Redistributable Code. (NOTE: The foregoing license grant
does not apply to files designated as Dbgrid.ocx and Graph32.ocx). You are
authorized to exercise the foregoing rights provided that: 
(a)	you comply with Section 3.1.1, and 
(b)	your end user agrees to: (a) distribute the Extended Use Redistributable
Code in object code only in conjunction with and as a part of a software
application product developed by them that adds significant and primary
functionality to the Extended Use Redistributable Code; (b) not use Microsoft's
name, logo, or trademarks to market the End-User Application; (c) include a
valid copyright notice on the End-User Application; (d) indemnify, hold
harmless, and defend Microsoft from and against any claims or lawsuits,
including attorney's fees, that arise or result from the use or distribution of
the End-User Application; and (e) not permit further distribution of the
Extended Use Redistributable Code by the user of the End-User Application.
3.1.3	Redistributable Code-Limited Use. If you are authorized and choose to
redistribute Limited Use Redistributable Code, in addition to the terms of
Section 3.1.1, you must also comply with the following (as applicable to the
corresponding portions of the SOFTWARE PRODUCT identified in REDIST.TXT as
Limited Use Redistributable Code). 
3.1.3.1	"Jet" Files. If you redistribute the "Jet Files" (as identified in the
SOFTWARE PRODUCT) you agree to comply with the following additional
requirements: (a) your Licensed Product shall not substantially duplicate the
capabilities of Microsoft Access or, in the reasonable opinion of Microsoft,
compete with same; and (b) unless your Licensed Product requires your customers
to license Microsoft Access in order to operate, you shall not reproduce or use
any of the Jet Files for commercial distribution in conjunction with a general
purpose word processing, spreadsheet or database management software product, or
an integrated work or product suite whose components include a general purpose
word processing, spreadsheet, or database management software product except for
the exclusive use of importing data to the various formats supported by
Microsoft Access. Note: A product that includes limited word processing,
spreadsheet or database components along with other components which provide
significant and primary value, such as an accounting product with limited
spreadsheet capability, is not considered to be a "general purpose" product. 
3.1.3.2	Microsoft Data Access Components. If you redistribute the Microsoft Data
Access Component file identified as MDAC_TYP.EXE, you also agree to redistribute
such file in object code only in conjunction with and as a part of a Licensed
Product developed by you with a Microsoft development tool product that adds
significant and primary functionality to MDAC_TYP.EXE.

4.	MICROSOFT WINDOWS NT OPTION PACK COMPONENTS.  Notwithstanding anything to the
contrary contained in this EULA, solely for those portions of the SOFTWARE
PRODUCT identified as the Microsoft Windows NT Option Pack Components, the
following provisions apply.  Note that your use of the Microsoft Windows NT
Option Pack Components is (a) subject to your prior acquisition of a validly
licensed copy of certain Microsoft operating system or server products; and (b)
all capitalized terms in this Section  4 refer to those terms as defined in the
end user-license agreement for the Windows NT Option Pack Component referenced
in the respective paragraphs of this Section (all such terms are noted in brackets):
4.1	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT WINDOWS NT SERVER
4.0, MICROSOFT WINDOWS NT SERVER ENTERPRISE EDITION 4.0 OR MICROSOFT BACKOFFICE
2.5, THE FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT WINDOWS NT SERVER 4.0,
MICROSOFT WINDOWS NT SERVER ENTERPRISE EDITION 4.0, OR MICROSOFT BACKOFFICE 2.5,
YOU ARE NOT AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE THE WINDOWS NT SOFTWARE
COMPONENTS.  FOR PURPOSES OF THIS SECTION 4.1, THE "WINDOWS NT SOFTWARE
COMPONENTS" SHALL MEAN THE FOLLOWING SOFTWARE COMPONENTS:  MICROSOFT MESSAGE
QUEUE SERVER, MICROSOFT TRANSACTION SERVER, MICROSOFT INTERNET INFORMATION
SERVER AND THE INTERNET CONNECTION SERVICES FOR MICROSOFT REMOTE ACCESS SERVICE.
 EVEN IF YOU HAVE A RIGHT TO USE THE WINDOWS NT SOFTWARE COMPONENTS, YOU DO NOT
HAVE ANY RIGHT TO INSTALL, COPY OR OTHERWISE USE ANY OF THE OTHER WINDOWS NT
OPTION PACK COMPONENTS, UNLESS OTHERWISE PROVIDED IN A DIFFERENT PARAGRAPH OF
THIS SECTION.
4.1.1	General.  The Windows NT Software Components contain server software and
client software which are deemed part of the [Server Software] and [Client
Software], respectively, of Microsoft Windows NT Server 4.0 (either as a
standalone product or as a component of Microsoft BackOffice) or Microsoft
Windows NT Server, Enterprise Edition 4.0, as applicable.  If you have a valid
license for Microsoft Windows NT Server 4.0, Microsoft Windows NT Server
Enterprise Edition 4.0 or Microsoft BackOffice 2.5 (each referred to
individually as a ["SOFTWARE PRODUCT"]), you are authorized to use the Windows
NT Software Components under the terms and conditions of the EULA applicable to
such product, except as set forth herein.  
4.1.2	For Microsoft Windows NT Server-Client Access.  In addition to the [Client
Access] requirements currently set forth in the applicable EULA, you need a
separate [Client Access License] for Windows NT Server in order to access or
otherwise utilize the following Windows NT Server basic network/application
services or [Server Software] components:  Microsoft Message Queue Server
(sending or receiving messages from Microsoft Message Queue Server), Microsoft
Transaction Server (invoking component-based applications managed by Microsoft
Transaction Server), and Remote Access Service (accessing the server from a
remote location through a communications link).  Note:  Remote Access Service
includes the use of Internet Connection Services, including Internet
Authentication Services (validation or transference of a remote access request)
or Connection Point Services (remotely configuring the Microsoft Connection
Manager Client with new phone numbers or other data).  Performance or Benchmark
Testing.  You may not disclose the results of any benchmark test of either the
[Server Software] or [Client Software] for Microsoft Message Queue Server,
Microsoft Transaction Server or Microsoft Internet Information Server to any
third party without Microsoft's prior written approval.  Installation on a
Single [Server].  The [Server Software] components that make up the applicable
[SOFTWARE PRODUCT] may only be installed together for use on one [Server] and
may not be separated, unless otherwise provided herein.  Note on Microsoft Site
Server Express.  You may freely copy and distribute Microsoft Site Server
Express for your use on any computer within your organization.
4.1.3	For Microsoft Internet Information Server-Use. Notwithstanding anything to
the contrary contained in the applicable EULA, you do not need a separate
[Client Access License] to access or otherwise utilize the services of Microsoft
Internet Information Server, except to the extent that a [Server] or [Server
Software] component which requires a [Client Access License] is accessed or
utilized by Microsoft Internet Information Server.
4.1.4	Additional Rights and Restrictions. You also have the right to make
additional copies of the Windows NT Software Components equal to the number of
validly licensed copies of each [SOFTWARE PRODUCT] which you have, and you may
use each copy in the manner specified above.  If you do not have a valid license
for Microsoft Windows NT Server 4.0, Microsoft Windows NT Server Enterprise
Edition 4.0 or Microsoft BackOffice 2.5, you have no rights under the foregoing
section.  
4.2	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT WINDOWS NT
WORKSTATION 4.0, THE FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT WINDOWS NT WORKSTATION
4.0, YOU ARE NOT AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE THE WINDOWS NT
WORKSTATION SOFTWARE COMPONENTS.  FOR PURPOSES OF THIS SECTION 4.2, THE "WINDOWS
NT WORKSTATION SOFTWARE 
COMPONENTS" SHALL MEAN THE FOLLOWING SOFTWARE COMPONENTS:  MICROSOFT TRANSACTION
SERVER AND MICROSOFT PERSONAL WEB SERVER.  EVEN IF YOU HAVE A RIGHT TO USE THE
WINDOWS NT WORKSTATION SOFTWARE COMPONENTS, YOU DO NOT HAVE ANY RIGHT TO
INSTALL, COPY OR USE ANY OF THE OTHER SOFTWARE COMPONENTS, UNLESS OTHERWISE
PROVIDED IN A DIFFERENT PARAGRAPH OF THIS SECTION.
4.2.1	General. The Windows NT Workstation Software Components are deemed part of
Microsoft Windows NT Workstation 4.0 (the ["SOFTWARE PRODUCT"]), and are
therefore subject to the terms and conditions of its EULA, except as otherwise
provided herein.  Use Limitation. At any point in time, only a maximum of two
(2) computers (instead of ten (10) are permitted to use the services of the
Microsoft Transaction Server component.  The two (2) computer maximum includes
any indirect uses made through software or hardware which pools or aggregates
uses.  Performance or Benchmark Testing. You may not disclose the results of any
benchmark test of either of the Windows NT Workstation Software Components to
any third party without Microsoft's prior written approval.
4.2.2	Additional Rights and Restrictions.  You also have the right to make
additional copies of the Windows NT Workstation Software Components equal to the
number of validly licensed copies of Microsoft Windows NT Workstation 4.0 which
you have, and you may use each copy in the manner specified above.  If you do
not have a valid license for Microsoft Windows NT Workstation 4.0, you have no
rights under the foregoing section.
4.3	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT BACKOFFICE SMALL
BUSINESS SERVER 4.0, THE FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT BACKOFFICE SMALL
BUSINESS SERVER 4.0, YOU ARE NOT AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE
THE WINDOWS NT SOFTWARE COMPONENTS (AS DEFINED PREVIOUSLY IN SECTION 4.1).  EVEN
IF YOU HAVE THE RIGHT TO USE THE WINDOWS NT SOFTWARE COMPONENTS, YOU DO NOT HAVE
ANY RIGHT TO INSTALL, COPY OR OTHERWISE USE ANY OF THE OTHER SOFTWARE
COMPONENTS, UNLESS OTHERWISE PROVIDED IN A DIFFERENT PARAGRAPH OF THIS SECTION.
4.3.1	General. The Windows NT Software Components contain server software and
client software which is deemed part of the [Server Software] and [Client
Software], respectively, of Microsoft BackOffice Small Business Server 4.0, and
is therefore subject to the terms and conditions of its EULA, except as
otherwise provided herein.  Note on Microsoft Site Server Express. You may
freely copy and distribute Microsoft Site Server Express for your use on any
computer within your organization.
4.3.2	Additional Rights and Restrictions. You also have the right to make
additional copies of the Windows NT Software Components equal to the number of
validly licensed copies of Microsoft BackOffice Small Business Server 4.0 which
you have, and you may use each copy in the manner specified above.  If you do
not have a valid license for Microsoft BackOffice Small Business Server 4.0, you
have no rights under the foregoing section.
4.4	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT WINDOWS 95, THE
FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT WINDOWS 95, YOU ARE NOT
AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE THE WINDOWS 95 SOFTWARE COMPONENTS.
 FOR PURPOSES OF THIS SECTION 4.4, THE "WINDOWS 95 SOFTWARE COMPONENTS" SHALL
MEAN THE FOLLOWING SOFTWARE COMPONENTS:  MICROSOFT PERSONAL WEB SERVER AND
MICROSOFT TRANSACTION SERVER FOR WINDOWS 95.  EVEN IF YOU HAVE A RIGHT TO USE
THE WINDOWS 95 SOFTWARE COMPONENTS, YOU DO NOT HAVE ANY RIGHT TO INSTALL, COPY
OR USE ANY OF THE OTHER SOFTWARE COMPONENTS, UNLESS OTHERWISE PROVIDED IN A
DIFFERENT PARAGRAPH OF THIS SECTION.
4.4.1	General. The Windows 95 Software Components are deemed part of Microsoft
Windows 95 (the ["SOFTWARE PRODUCT"]), and are therefore subject to the terms
and conditions of its EULA, except as otherwise provided herein.
4.4.2	Use Limitation. At any point in time, a maximum of ten (10) computers are
permitted to use the services of the Microsoft Personal Web Server component. 
The ten (10) computer maximum includes any indirect uses made through software
or hardware which pools or aggregates uses. The Microsoft Transaction Server for
Windows 95 component may not be used as a network server; that is, no computers
or workstations may access or utilize any network services of that component.
Performance or Benchmark Testing. You may not disclose the results of any
benchmark test of either of the Windows 95 Software Components to any third
party without Microsoft's prior written approval.
4.4.3	Additional Rights and Restrictions. You also have the right to make
additional copies of the Windows 95 Software Components equal to the number of
validly licensed copies of Microsoft Windows 95 which you have, and you may use
each copy in the manner specified above.  If you do not have a valid license for
Microsoft Windows 95, you have no rights under the foregoing section.

5.	DESCRIPTION OF OTHER RIGHTS AND LIMITATIONS
5.1	Not For Resale Software. If the SOFTWARE PRODUCT is labeled "Not For Resale"
or "NFR," then you may not resell, or otherwise transfer for value, the SOFTWARE
PRODUCT.
5.2	Limitations on Reverse Engineering, Decompilation, and Disassembly. You may
not reverse engineer, decompile, or disassemble the SOFTWARE PRODUCT, except and
only to the extent that such activity is expressly permitted by applicable law
notwithstanding this limitation.
5.3	Rental. You may not rent, lease, or lend the SOFTWARE PRODUCT.
5.4	Trademarks. This EULA does not grant you any rights in connection with any
trademarks or service marks of Microsoft.
5.5	Support Services. Microsoft may provide you with support services related to
the SOFTWARE PRODUCT ("Support Services"). Use of Support Services is governed
by the Microsoft policies and programs described in the user manual, in "online"
documentation 
and/or in other Microsoft-provided materials. Any supplemental software code
provided to you as part of the Support Services shall be considered part of the
SOFTWARE PRODUCT and subject to the terms and conditions of this EULA. With
respect to technical information you provide to Microsoft as part of the Support
Services, Microsoft may use such information for its business purposes,
including for product support and development. Microsoft will not utilize such
technical information in a form that personally identifies you.
5.6	Software Transfer. The initial user of the SOFTWARE PRODUCT may make a
one-time permanent transfer of this EULA and SOFTWARE PRODUCT only directly to
an end user. This transfer must include all of the SOFTWARE PRODUCT (including
all component parts, the media and printed materials, any upgrades, this EULA,
and, if applicable, the Certificate of Authenticity). Such transfer may not be
by way of consignment or any other indirect transfer. The transferee of such
one-time transfer must agree to comply with the terms of this EULA, including
the obligation not to further transfer this EULA and SOFTWARE PRODUCT.
5.7	Separation of Components. The SOFTWARE PRODUCT is licensed as a single
product. Its component parts may not be separated for use by more than one user. 
5.8	Termination. Without prejudice to any other rights, Microsoft may terminate
this EULA if you fail to comply with the terms and conditions of this EULA. In
such event, you must destroy all copies of the SOFTWARE PRODUCT and all of its
component parts.

6.	PRERELEASE CODE. Portions of the SOFTWARE PRODUCT may be identified as
prerelease code ("Prerelease Code"). Such Prerelease Code is not at the level of
performance and compatibility of the final, generally available product
offering. The Prerelease Code may not operate correctly and may be substantially
modified prior to first commercial shipment. Microsoft is not obligated to make
this or any later version of the Prerelease Code commercially available. The
grant of license to use Prerelease Code expires upon availability of a
commercial release of the Prerelease Code from Microsoft. NOTE: In the event
that Prerelease Code contains a separate end-user license agreement, the terms
and conditions of such end-user license agreement shall govern your use of the
corresponding Prerelease Code.

7.	UPGRADES. If the SOFTWARE PRODUCT is labeled as an upgrade, you must be
properly licensed to use a product identified by Microsoft as being eligible for
the upgrade in order to use the SOFTWARE PRODUCT. A SOFTWARE PRODUCT labeled as
an upgrade replaces and/or supplements the product that formed the basis for
your eligibility for the upgrade. You may use the resulting upgraded product
only in accordance with the terms of this EULA. If the SOFTWARE PRODUCT is an
upgrade of a component of a package of software programs that you licensed as a
single product, the SOFTWARE PRODUCT may be used and transferred only as part of
that single product package and may not be separated for use on more than one
computer.

8.	COPYRIGHT. All title and intellectual property rights in and to the SOFTWARE
PRODUCT (including but not limited to any images, photographs, animations,
video, audio, music, text, and "applets" incorporated into the SOFTWARE
PRODUCT), the accompanying printed materials, and any copies of the SOFTWARE
PRODUCT are owned by Microsoft or its suppliers. All title and intellectual
property rights in and to the content which may be accessed through use of the
SOFTWARE PRODUCT is the property of the respective content owner and may be
protected by applicable copyright or other intellectual property laws and
treaties. This EULA grants you no rights to use such content. All rights not
expressly granted are reserved by Microsoft.

9.	U.S. GOVERNMENT RESTRICTED RIGHTS. The SOFTWARE PRODUCT and documentation are
provided with RESTRICTED RIGHTS. Use, duplication, or disclosure by the
Government is subject to restrictions as set forth in subparagraph (c)(1)(ii) of
the Rights in Technical Data and Computer Software clause at DFARS 252.227-7013
or subparagraphs (c)(1) and (2) of the Commercial Computer Software-Restricted
Rights at 58 CFR 52.227-19, as applicable. Manufacturer is Microsoft
Corporation/One Microsoft Way/Redmond, WA 98052-6399.

10.	EXPORT RESTRICTIONS. You agree that you will not export or re-export the
SOFTWARE PRODUCT, any part thereof, or any process or service that is the direct
product of the SOFTWARE PRODUCT (the foregoing collectively referred to as the
"Restricted Components"), to any country, person, entity or end user subject to
U.S. export restrictions. You specifically agree not to export or re-export any
of the Restricted Components (i) to any country to which the U.S. has embargoed
or restricted the export of goods or services, which currently include, but are
not necessarily limited to Cuba, Iran, Iraq, Libya, North Korea, Sudan and
Syria, or to any national of any such country, wherever located, who intends to
transmit or transport the Restricted Components back to such country; (ii) to
any end-user who you know or have reason to know will utilize the Restricted
Components in the design, development or production of nuclear, chemical or
biological weapons; or (iii) to any end-user who has been prohibited from
participating in U.S. export transactions by any federal agency of the U.S.
government. You warrant and represent that neither the BXA nor any other U.S.
federal agency has suspended, revoked, or denied your export privileges.

11.	NOTE ON JAVA SUPPORT. THE SOFTWARE PRODUCT CONTAINS SUPPORT FOR PROGRAMS
WRITTEN IN JAVA. JAVA TECHNOLOGY IS NOT FAULT TOLERANT AND IS NOT DESIGNED,
MANUFACTURED, OR INTENDED FOR USE OR RESALE AS ONLINE CONTROL EQUIPMENT IN
HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE PERFORMANCE, SUCH AS IN THE OPERATION
OF NUCLEAR FACILITIES, AIRCRAFT NAVIGATION OR COMMUNICATIONS SYSTEMS, AIR
TRAFFIC CONTROL, DIRECT LIFE SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE
FAILURE OF JAVA TECHNOLOGY COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR
SEVERE PHYSICAL OR ENVIRONMENTAL DAMAGE.
MISCELLANEOUS
If you acquired this product in the United States, this EULA is governed by the
laws of the State of Washington. 
If you acquired this product in Canada, this EULA is governed by the laws of the
Province of Ontario, Canada. Each of the parties hereto irrevocably attorns to
the jurisdiction of the courts of the Province of Ontario and further agrees to
commence any litigation which may arise hereunder in the courts located in the
Judicial District of York, Province of Ontario. 
If this product was acquired outside the United States, then local law may apply.
Should you have any questions concerning this EULA, or if you desire to contact
Microsoft for any reason, please contact Microsoft, or write: Microsoft Sales
Information Center/One Microsoft Way/Redmond, WA 98052-6399. 

LIMITED WARRANTY
LIMITED WARRANTY. Except with respect to the REDISTRIBUTABLES, which are
provided "as is," without warranty of any kind,  Microsoft warrants that (a) the
SOFTWARE PRODUCT will perform substantially in accordance with the accompanying
written materials for a period of ninety (90) days from the date of receipt, and
(b) any Support Services provided by Microsoft shall be substantially as
described in applicable written materials provided to you by Microsoft, and
Microsoft support engineers will make commercially reasonable efforts to solve
any problem. To the extent allowed by applicable law, implied warranties on the
SOFTWARE PRODUCT, if any, are limited to ninety (90) days. Some
states/jurisdictions do not allow limitations on duration of an implied
warranty, so the above limitation may not apply to you.

CUSTOMER REMEDIES. Microsoft's and its suppliers' entire liability and your
exclusive remedy shall be, at Microsoft's option, either (a) return of the price
paid, if any, or (b) repair or replacement of the SOFTWARE PRODUCT that does not
meet Microsoft's Limited Warranty and that is returned to Microsoft with a copy
of your receipt. This Limited Warranty is void if failure of the SOFTWARE
PRODUCT has resulted from accident, abuse, or misapplication. Any replacement
SOFTWARE PRODUCT will be warranted for the remainder of the original warranty
period or thirty (30) days, whichever is longer. Outside the United States,
neither these remedies nor any product support services offered by Microsoft are
available without proof of purchase from an authorized international source.

NO OTHER WARRANTIES. TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW,
MICROSOFT AND ITS SUPPLIERS DISCLAIM ALL OTHER WARRANTIES AND CONDITIONS, EITHER
EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, IMPLIED WARRANTIES OR
CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, TITLE AND
NON-INFRINGEMENT, WITH REGARD TO THE SOFTWARE PRODUCT, AND THE PROVISION OF OR
FAILURE TO PROVIDE SUPPORT SERVICES. THIS LIMITED WARRANTY GIVES YOU SPECIFIC
LEGAL RIGHTS. YOU MAY HAVE OTHERS, WHICH VARY FROM STATE/JURISDICTION TO
STATE/JURISDICTION.

LIMITATION OF LIABILITY. TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW, IN
NO EVENT SHALL MICROSOFT OR ITS SUPPLIERS BE LIABLE FOR ANY SPECIAL, INCIDENTAL,
INDIRECT, OR CONSEQUENTIAL DAMAGES WHATSOEVER (INCLUDING, WITHOUT LIMITATION,
DAMAGES FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, LOSS OF BUSINESS
INFORMATION, OR ANY OTHER PECUNIARY LOSS) ARISING OUT OF THE USE OF OR INABILITY
TO USE THE SOFTWARE PRODUCT OR THE FAILURE TO PROVIDE SUPPORT SERVICES, EVEN IF
MICROSOFT HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. IN ANY CASE,
MICROSOFT'S ENTIRE LIABILITY UNDER ANY PROVISION OF THIS EULA SHALL BE LIMITED
TO THE GREATER OF THE AMOUNT ACTUALLY PAID BY YOU FOR THE SOFTWARE PRODUCT OR
U.S.$5.00; PROVIDED, HOWEVER, IF YOU HAVE ENTERED INTO A MICROSOFT SUPPORT
SERVICES AGREEMENT, MICROSOFT'S ENTIRE LIABILITY REGARDING SUPPORT SERVICES
SHALL BE GOVERNED BY THE TERMS OF THAT AGREEMENT. BECAUSE SOME
STATES/JURISDICTIONS DO NOT ALLOW THE EXCLUSION OR LIMITATION OF LIABILITY, THE
ABOVE LIMITATION MAY NOT APPLY TO YOU.


Si vous avez acquis votre produit Microsoft au CANADA, la garantie
limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe
suivante vous concerne :

GARANTIE LIMITEE
GARANTIE LIMITýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýE - Sauf pour celles du
REDISTRIBUTABLES, qui sont
fournies
"comme telles," sans acune garantie quelle qu'elle soit,  Microsoft garantit que
(a) la performance du LOGICIEL sera substantiellement en
conformitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý avec la
documentation qui accompagne le LOGICIEL, pour une
pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriode de
quatre-vingt-dix
(90) jours ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý compter de la date de
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýception; et
(b) tout support
technique
fourni par Microsoft sera substantiellement en
conformitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý avec toute
documentation affýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýrente fournie par Microsoft et
que les membres
du support
technique de Microsoft feront des efforts raisonnables pour
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsoudre toute
difficultýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý technique
dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýcoulant de l'utilisation du
LOGICIEL. Certaines
juridictions ne permettent pas de limiter dans le temps l'application de la
prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente garantie. Aussi, la limite
stipulýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe
ci-haut pourrait ne pas
s'appliquer
dans votre cas. Dans la mesure permise par la loi, toute garantie implicite
portant sur le LOGICIEL, le cas
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýchýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýant, est
limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý une pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriode de
quatre-vingt-dix (90) jours.

RECOURS DU CLIENT - La seule obligation de Microsoft et de ses fournisseurs et
votre recours exclusif seront, au choix de Microsoft, soit (a) le remboursement
du prix payýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý, si applicable, ou (b) la
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýparation
ou le
remplacement du
LOGICIEL
qui n'est pas conforme ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý la Garantie
Limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe de
Microsoft et qui est
retournýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý
Microsoft avec une copie de votre reýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýu. Cette
Garantie
Limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe est
nulle si le
dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýfaut du LOGICIEL est
causýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý par un accident, un
traitement abusif
ou une
mauvaise application. Tout LOGICIEL de remplacement sera garanti pour le reste
de la pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriode de garantie initiale ou pour trente
(30) jours,
selon la plus
longue de ces pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriodes. A
l'extýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýrieur des
Etats-Unis, aucun de ces
recours non
plus que le support technique offert par Microsoft ne sont disponibles sans une
preuve d'achat provenant d'une source authorisýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe. 

AUCUNE AUTRE GARANTIE - DANS LA MESURE PREVUE PAR LA LOI, MICROSOFT ET SES
FOURNISSEURS EXCLUENT TOUTE AUTRE GARANTIE OU CONDITION, EXPRESSE OU IMPLICITE,
Y COMPRIS MAIS NE SE LIMITANT PAS AUX GARANTIES OU CONDITIONS IMPLICITES DU
CARACTERE ADEQUAT POUR LA COMMERCIALISATION OU UN USAGE PARTICULIER EN CE QUI
CONCERNE LE LOGICIEL OU CONCERNANT LE TITRE, L'ABSENCE DE
CONTREFAýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýON DUDIT
LOGICIEL, ET TOUTE DOCUMENTATION ECRITE QUI L'ACCOMPAGNE, AINSI QUE POUR TOUTE
DISPOSITION CONCERNANT LE SUPORT TECHNIQUE OU LA
FAýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýON DONT
CELUI-CI A ETE
RENDU. CETTE GARANTIE LIMITEE VOUS ACCORDE DES DROITS JURIDIQUES SPECIFIQUES. 

PAS DE RESPONSABILITE POUR LES DOMMAGES INDIRECTS - MICROSOFT OU SES
FOURNISSEURS NE SERONT PAS RESPONSABLES, EN AUCUNE CIRCONSTANCE, POUR TOUT
DOMMAGE SPECIAL, INCIDENT, INDIRECT, OU CONSEQUENT QUEL QU'IL SOIT (Y COMPRIS,
SANS LIMITATION, LES DOMMAGES ENTRAINES PAR LA PERTE DE BENEFICES,
L'INTERRUPTION DES ACTIVITES, LA PERTE D'INFORMATION OU TOUTE AUTRE PERTE
PECUNIAIRE) DECOULANT DE OU RELIE A LA LICENCE D'ACCES DU CLIENTET CE, MEME SI
MICROSOFT A ETE AVISEE DE LA POSSIBILITE DE TELS DOMMAGES. LA RESPONSABILITE DE
MICROSOFT EN VERTU DE TOUTE DISPOSITION DE CETTE CONVENTION NE POURRA EN AUCUN
TEMPS EXCEDER LE PLUS ELEVE ENTRE I) LE MONTANT EFFECTIVEMENT PAYE PAR VOUS POUR
LA LICENCE D'ACCES DU CLIENT OU II) U.S.$5.00. ADVENANT QUE VOUS AYEZ CONTRACTE
PAR ENTENTE DISTINCTE AVEC MICROSOFT POUR UN SUPPORT TECHNIQUE ETENDU, VOUS
SEREZ LIE PAR LES TERMES D' UNE TELLE ENTENTE.

La prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente Convention est
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýgie par les lois en
vigeur dans ela
province
d'Ontario, Canada. Chacune des parties ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý la
prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente reconnaýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýt
irrýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýývocablement
la compýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýtence des tribunaux de la province
d'Ontario et consent
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý
instituer tout
litige qui pourrait dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýcouler de la
prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente
auprýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýs des
tribunaux
situýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýs dans le
district judiciaire de York, province d'Ontario.

Au cas oýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý vous auriez des questions concernant
cette licence ou
que vous
dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsiriez vous mettre en rapport avec Microsoft
pour quelque
raison que ce
soit,
veuillez contacter la succursale Microsoft desservant votre pays, dont l'adresse
est fournie dans ce produit, ou ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýcrire
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý:
Microsoft Sales
Information Center, 
One Microsoft Way, Redmond, Washington 98052-6399.

***************************************************************************

%%The following software may be included in this product:
msvcr80.dll from Microsoft C++

Use of any of this software is governed by the terms of the license below:

END-USER LICENSE AGREEMENT FOR MICROSOFT SOFTWARE

IMPORTANT-READ CAREFULLY: This Microsoft End-User License Agreement (''EULA'')
is a legal agreement between you (either an individual or a single entity) and
Microsoft Corporation for the Microsoft software product(s) accompanying this
EULA, which include(s) computer software and may include "online" or electronic
documentation, associated media, and printed materials (''SOFTWARE PRODUCT'').
By installing, copying, or otherwise using the SOFTWARE PRODUCT or any UPDATES
(as defined below), you agree to be bound by the terms of this EULA. If you do
not agree to the terms of this EULA, do not install, copy, or otherwise use the
SOFTWARE PRODUCT; you may, however, return it to your place of purchase for a
full refund. In addition, by installing, copying, or otherwise using any updates
or other components of the SOFTWARE PRODUCT that you receive separately as part
of the SOFTWARE PRODUCT (''UPDATES''), you agree to be bound by any additional
license terms that accompany such UPDATES. If you do not agree to the additional
license terms that accompany such UPDATES, you may not install, copy, or
otherwise use such UPDATES.

SOFTWARE PRODUCT LICENSE
The SOFTWARE PRODUCT is protected by copyright laws and international copyright
treaties, as well as other intellectual property laws and treaties. The SOFTWARE
PRODUCT is licensed, not sold. NOTE: The terms of a printed, paper EULA which
may accompany the SOFTWARE PRODUCT supersede the terms of any on-screen EULA
found within the SOFTWARE PRODUCT.

1.	LICENSE TO USE SOFTWARE PRODUCT. 
1.1	General License Grant. Microsoft grants to you as an individual, a personal,
nonexclusive license to make and use copies of the SOFTWARE PRODUCT for the sole
purposes of designing, developing, and testing your software product(s) that are
designed to operate in conjunction with any Microsoft operating system product.
You may install copies of the SOFTWARE PRODUCT on an unlimited number of
computers provided that you are the only individual using the SOFTWARE PRODUCT.
If you are an entity, Microsoft grants you the right to designate one individual
within your organization to have the sole right to use the SOFTWARE PRODUCT in
the manner provided above.
1.2	Documentation. This EULA grants you, as an individual, a personal,
nonexclusive license to make and use an unlimited number of copies of any
documentation, provided that such copies shall be used only for personal
purposes and are not to be republished or distributed (either in hard copy or
electronic form) beyond the user's premises and with the following exception:
you may use documentation identified in the MSDN Library portion of the SOFTWARE
PRODUCT as the file format specification for Microsoft Word, Microsoft Excel,
Microsoft Access, and/or Microsoft PowerPoint ("File Format Documentation")
solely in connection with your development of software product(s) that operate
in conjunction with Windows or Windows NT that are not general purpose word
processing, spreadsheet, or database management software products or an
integrated work or product suite whose components include one or more general
purpose word processing, spreadsheet, or database management software products.
Note: A product that includes limited word processing, spreadsheet, or database
components along with other components that provide significant and primary
value, such as an accounting product with limited spreadsheet capability, is not
considered to be a "general purpose" product.
1.3	Storage/Network Use. You may also store or install a copy of the SOFTWARE
PRODUCT on a storage device, such as a network server, used only to install or
run the SOFTWARE PRODUCT on computers used by a licensed end user in accordance
with Section 1.1.  A single license for the SOFTWARE PRODUCT may not be shared
or used concurrently by other end users.
1.4	Visual Studio-Effect of EULA. This Section 1.4 also applies if the SOFTWARE
PRODUCT is Microsoft Visual Studio, a suite of development tools and other
software programs (each such tool or software program, a "Component").
Components that you receive as part of the SOFTWARE PRODUCT may include a
separate end-user license agreement (each, a "Component EULA"). Except as
provided in Section 6, in the event of inconsistencies between this EULA and any
Component EULA, the terms of this EULA shall control.
1.5	Microsoft Internet Explorer. You may make and use copies of the Microsoft
Internet Explorer for use on all computers for which you have a validly licensed
copy of Microsoft operating system products. 

2.	REDISTRIBUTABLE CODE-ADDITIONAL LICENSE RIGHTS. In addition to the rights
granted in Section 1, certain portions of the SOFTWARE PRODUCT, as described in
this Section 2, are provided to you with additional license rights provided that
you comply with the terms of 
Section 3.1.
2.1	Sample Code. Microsoft grants you the right to use and modify the source
code version of those portions of the SOFTWARE PRODUCT identified as "Samples"
in REDIST.TXT or elsewhere in the SOFTWARE PRODUCT ("Sample Code") for the sole
purposes of designing, developing, and testing your software product(s), and to
reproduce and distribute the Sample Code, along with any modifications thereof,
only in object code form.
2.2	Redistributable Code-Standard. Microsoft grants you a nonexclusive,
royalty-free right to reproduce and distribute the object code form of any
portion of the SOFTWARE PRODUCT listed in REDIST.TXT ("Redistributable Code").
NOTE: certain Redistributable Code may be subject to the restrictions in Section
2.3 if it is also identified as "Limited Use Redistributable Code."
2.3	Redistributable Code-Limited Use. Provided that you ALSO comply with the
terms of Section 3.1.3, Microsoft grants you a nonexclusive, royalty-free right
to reproduce and distribute the object code form of those portions of the
SOFTWARE PRODUCT listed in REDIST.TXT as Limited Use Redistributable Code
("Limited Use Redistributable Code").
2.4	Visual C++ and Visual Studio-Microsoft Foundation Classes (MFC), Template
Libraries (ATL), and C runtimes (CRTs). If this EULA accompanies Visual C++ or
Visual Studio, then in addition to the rights granted in Section 1, Microsoft
grants you the right to use and modify the source code version of those portions
of the SOFTWARE PRODUCT that are identified as MFC, ATL, or CRTs (collectively,
the "VC Redistributables"), for the sole purposes of designing, developing, and
testing your software product(s). Provided you comply with Section 3.1 and you
rename any files created by you that are included in the Licensed Product
(defined below), Microsoft grants you a nonexclusive, royalty-free right to
reproduce and distribute the object code version of the VC Redistributables,
including any modifications you make. For purposes of this section,
"modifications" shall mean enhancements to the functionality of the VC
Redistributables.

3.	DISTRIBUTION REQUIREMENTS; LICENSE RESTRICTIONS. 
3.1	General. The SOFTWARE PRODUCT may contain up to three categories of
redistributable code, any redistribution of which by you requires compliance
with the following terms. 
3.1.1.	Redistributable Code-Standard. If you are authorized and choose to
redistribute Sample Code, Redistributable Code, and Limited Use Redistributable
Code, (collectively, the "Redistributables") as described in Section 2, you
agree to: (a) distribute the Redistributables in object code form and only in
conjunction with and as a part of a software application product developed by
you using the product accompanying this EULA that adds significant and primary
functionality to the SOFTWARE PRODUCT ("Licensed Product"); (b) not use
Microsoft's name, logo, or trademarks to market the Licensed Product; (c)
include a valid copyright notice on the Licensed Product; (d) indemnify, hold
harmless, and defend Microsoft from and against any claims or lawsuits,
including attorney's fees, that arise or result from the use or distribution of
the Licensed Product; (e) include "Copyright  Microsoft Systems Journal"
in all Microsoft Systems Journal (MSJ) code used within your program(s); (f)
otherwise comply with the terms of this EULA; and (g) agree that Microsoft
reserves all rights not expressly granted. You also agree not to permit further
distribution of the Redistributables by your end users except: (1) you may
permit further redistribution of the Redistributables by your distributors to
your end-user customers if your distributors only distribute the
Redistributables in conjunction with, and as part of, the Licensed Product and
you and your distributors comply with all other terms of this EULA; and (2) in
the manner described in Section 3.1.2. 
3.1.2	Redistributable Code-Extended Use. Visual Basic, Visual C++, Visual J++,
and Visual Studio. If this EULA accompanies any of the Microsoft products listed
in the heading of this subsection, and subject to your compliance with Section
3.1.1, you may permit your end users to reproduce and distribute the object code
form of certain portions of the SOFTWARE PRODUCT (as listed in REDIST.TXT as
"Extended Use Redistributable Code") only in conjunction with and part of a
Licensed Product and/or Web page that adds significant and primary functionality
to the Extended Use Redistributable Code. (NOTE: The foregoing license grant
does not apply to files designated as Dbgrid.ocx and Graph32.ocx). You are
authorized to exercise the foregoing rights provided that: 
(a)	you comply with Section 3.1.1, and 
(b)	your end user agrees to: (a) distribute the Extended Use Redistributable
Code in object code only in conjunction with and as a part of a software
application product developed by them that adds significant and primary
functionality to the Extended Use Redistributable Code; (b) not use Microsoft's
name, logo, or trademarks to market the End-User Application; (c) include a
valid copyright notice on the End-User Application; (d) indemnify, hold
harmless, and defend Microsoft from and against any claims or lawsuits,
including attorney's fees, that arise or result from the use or distribution of
the End-User Application; and (e) not permit further distribution of the
Extended Use Redistributable Code by the user of the End-User Application.
3.1.3	Redistributable Code-Limited Use. If you are authorized and choose to
redistribute Limited Use Redistributable Code, in addition to the terms of
Section 3.1.1, you must also comply with the following (as applicable to the
corresponding portions of the SOFTWARE PRODUCT identified in REDIST.TXT as
Limited Use Redistributable Code). 
3.1.3.1	"Jet" Files. If you redistribute the "Jet Files" (as identified in the
SOFTWARE PRODUCT) you agree to comply with the following additional
requirements: (a) your Licensed Product shall not substantially duplicate the
capabilities of Microsoft Access or, in the reasonable opinion of Microsoft,
compete with same; and (b) unless your Licensed Product requires your customers
to license Microsoft Access in order to operate, you shall not reproduce or use
any of the Jet Files for commercial distribution in conjunction with a general
purpose word processing, spreadsheet or database management software product, or
an integrated work or product suite whose components include a general purpose
word processing, spreadsheet, or database management software product except for
the exclusive use of importing data to the various formats supported by
Microsoft Access. Note: A product that includes limited word processing,
spreadsheet or database components along with other components which provide
significant and primary value, such as an accounting product with limited
spreadsheet capability, is not considered to be a "general purpose" product. 
3.1.3.2	Microsoft Data Access Components. If you redistribute the Microsoft Data
Access Component file identified as MDAC_TYP.EXE, you also agree to redistribute
such file in object code only in conjunction with and as a part of a Licensed
Product developed by you with a Microsoft development tool product that adds
significant and primary functionality to MDAC_TYP.EXE.

4.	MICROSOFT WINDOWS NT OPTION PACK COMPONENTS.  Notwithstanding anything to the
contrary contained in this EULA, solely for those portions of the SOFTWARE
PRODUCT identified as the Microsoft Windows NT Option Pack Components, the
following provisions apply.  Note that your use of the Microsoft Windows NT
Option Pack Components is (a) subject to your prior acquisition of a validly
licensed copy of certain Microsoft operating system or server products; and (b)
all capitalized terms in this Section  4 refer to those terms as defined in the
end user-license agreement for the Windows NT Option Pack Component referenced
in the respective paragraphs of this Section (all such terms are noted in brackets):
4.1	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT WINDOWS NT SERVER
4.0, MICROSOFT WINDOWS NT SERVER ENTERPRISE EDITION 4.0 OR MICROSOFT BACKOFFICE
2.5, THE FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT WINDOWS NT SERVER 4.0,
MICROSOFT WINDOWS NT SERVER ENTERPRISE EDITION 4.0, OR MICROSOFT BACKOFFICE 2.5,
YOU ARE NOT AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE THE WINDOWS NT SOFTWARE
COMPONENTS.  FOR PURPOSES OF THIS SECTION 4.1, THE "WINDOWS NT SOFTWARE
COMPONENTS" SHALL MEAN THE FOLLOWING SOFTWARE COMPONENTS:  MICROSOFT MESSAGE
QUEUE SERVER, MICROSOFT TRANSACTION SERVER, MICROSOFT INTERNET INFORMATION
SERVER AND THE INTERNET CONNECTION SERVICES FOR MICROSOFT REMOTE ACCESS SERVICE.
 EVEN IF YOU HAVE A RIGHT TO USE THE WINDOWS NT SOFTWARE COMPONENTS, YOU DO NOT
HAVE ANY RIGHT TO INSTALL, COPY OR OTHERWISE USE ANY OF THE OTHER WINDOWS NT
OPTION PACK COMPONENTS, UNLESS OTHERWISE PROVIDED IN A DIFFERENT PARAGRAPH OF
THIS SECTION.
4.1.1	General.  The Windows NT Software Components contain server software and
client software which are deemed part of the [Server Software] and [Client
Software], respectively, of Microsoft Windows NT Server 4.0 (either as a
standalone product or as a component of Microsoft BackOffice) or Microsoft
Windows NT Server, Enterprise Edition 4.0, as applicable.  If you have a valid
license for Microsoft Windows NT Server 4.0, Microsoft Windows NT Server
Enterprise Edition 4.0 or Microsoft BackOffice 2.5 (each referred to
individually as a ["SOFTWARE PRODUCT"]), you are authorized to use the Windows
NT Software Components under the terms and conditions of the EULA applicable to
such product, except as set forth herein.  
4.1.2	For Microsoft Windows NT Server-Client Access.  In addition to the [Client
Access] requirements currently set forth in the applicable EULA, you need a
separate [Client Access License] for Windows NT Server in order to access or
otherwise utilize the following Windows NT Server basic network/application
services or [Server Software] components:  Microsoft Message Queue Server
(sending or receiving messages from Microsoft Message Queue Server), Microsoft
Transaction Server (invoking component-based applications managed by Microsoft
Transaction Server), and Remote Access Service (accessing the server from a
remote location through a communications link).  Note:  Remote Access Service
includes the use of Internet Connection Services, including Internet
Authentication Services (validation or transference of a remote access request)
or Connection Point Services (remotely configuring the Microsoft Connection
Manager Client with new phone numbers or other data).  Performance or Benchmark
Testing.  You may not disclose the results of any benchmark test of either the
[Server Software] or [Client Software] for Microsoft Message Queue Server,
Microsoft Transaction Server or Microsoft Internet Information Server to any
third party without Microsoft's prior written approval.  Installation on a
Single [Server].  The [Server Software] components that make up the applicable
[SOFTWARE PRODUCT] may only be installed together for use on one [Server] and
may not be separated, unless otherwise provided herein.  Note on Microsoft Site
Server Express.  You may freely copy and distribute Microsoft Site Server
Express for your use on any computer within your organization.
4.1.3	For Microsoft Internet Information Server-Use. Notwithstanding anything to
the contrary contained in the applicable EULA, you do not need a separate
[Client Access License] to access or otherwise utilize the services of Microsoft
Internet Information Server, except to the extent that a [Server] or [Server
Software] component which requires a [Client Access License] is accessed or
utilized by Microsoft Internet Information Server.
4.1.4	Additional Rights and Restrictions. You also have the right to make
additional copies of the Windows NT Software Components equal to the number of
validly licensed copies of each [SOFTWARE PRODUCT] which you have, and you may
use each copy in the manner specified above.  If you do not have a valid license
for Microsoft Windows NT Server 4.0, Microsoft Windows NT Server Enterprise
Edition 4.0 or Microsoft BackOffice 2.5, you have no rights under the foregoing
section.  
4.2	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT WINDOWS NT
WORKSTATION 4.0, THE FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT WINDOWS NT WORKSTATION
4.0, YOU ARE NOT AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE THE WINDOWS NT
WORKSTATION SOFTWARE COMPONENTS.  FOR PURPOSES OF THIS SECTION 4.2, THE "WINDOWS
NT WORKSTATION SOFTWARE 
COMPONENTS" SHALL MEAN THE FOLLOWING SOFTWARE COMPONENTS:  MICROSOFT TRANSACTION
SERVER AND MICROSOFT PERSONAL WEB SERVER.  EVEN IF YOU HAVE A RIGHT TO USE THE
WINDOWS NT WORKSTATION SOFTWARE COMPONENTS, YOU DO NOT HAVE ANY RIGHT TO
INSTALL, COPY OR USE ANY OF THE OTHER SOFTWARE COMPONENTS, UNLESS OTHERWISE
PROVIDED IN A DIFFERENT PARAGRAPH OF THIS SECTION.
4.2.1	General. The Windows NT Workstation Software Components are deemed part of
Microsoft Windows NT Workstation 4.0 (the ["SOFTWARE PRODUCT"]), and are
therefore subject to the terms and conditions of its EULA, except as otherwise
provided herein.  Use Limitation. At any point in time, only a maximum of two
(2) computers (instead of ten (10) are permitted to use the services of the
Microsoft Transaction Server component.  The two (2) computer maximum includes
any indirect uses made through software or hardware which pools or aggregates
uses.  Performance or Benchmark Testing. You may not disclose the results of any
benchmark test of either of the Windows NT Workstation Software Components to
any third party without Microsoft's prior written approval.
4.2.2	Additional Rights and Restrictions.  You also have the right to make
additional copies of the Windows NT Workstation Software Components equal to the
number of validly licensed copies of Microsoft Windows NT Workstation 4.0 which
you have, and you may use each copy in the manner specified above.  If you do
not have a valid license for Microsoft Windows NT Workstation 4.0, you have no
rights under the foregoing section.
4.3	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT BACKOFFICE SMALL
BUSINESS SERVER 4.0, THE FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT BACKOFFICE SMALL
BUSINESS SERVER 4.0, YOU ARE NOT AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE
THE WINDOWS NT SOFTWARE COMPONENTS (AS DEFINED PREVIOUSLY IN SECTION 4.1).  EVEN
IF YOU HAVE THE RIGHT TO USE THE WINDOWS NT SOFTWARE COMPONENTS, YOU DO NOT HAVE
ANY RIGHT TO INSTALL, COPY OR OTHERWISE USE ANY OF THE OTHER SOFTWARE
COMPONENTS, UNLESS OTHERWISE PROVIDED IN A DIFFERENT PARAGRAPH OF THIS SECTION.
4.3.1	General. The Windows NT Software Components contain server software and
client software which is deemed part of the [Server Software] and [Client
Software], respectively, of Microsoft BackOffice Small Business Server 4.0, and
is therefore subject to the terms and conditions of its EULA, except as
otherwise provided herein.  Note on Microsoft Site Server Express. You may
freely copy and distribute Microsoft Site Server Express for your use on any
computer within your organization.
4.3.2	Additional Rights and Restrictions. You also have the right to make
additional copies of the Windows NT Software Components equal to the number of
validly licensed copies of Microsoft BackOffice Small Business Server 4.0 which
you have, and you may use each copy in the manner specified above.  If you do
not have a valid license for Microsoft BackOffice Small Business Server 4.0, you
have no rights under the foregoing section.
4.4	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT WINDOWS 95, THE
FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT WINDOWS 95, YOU ARE NOT
AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE THE WINDOWS 95 SOFTWARE COMPONENTS.
 FOR PURPOSES OF THIS SECTION 4.4, THE "WINDOWS 95 SOFTWARE COMPONENTS" SHALL
MEAN THE FOLLOWING SOFTWARE COMPONENTS:  MICROSOFT PERSONAL WEB SERVER AND
MICROSOFT TRANSACTION SERVER FOR WINDOWS 95.  EVEN IF YOU HAVE A RIGHT TO USE
THE WINDOWS 95 SOFTWARE COMPONENTS, YOU DO NOT HAVE ANY RIGHT TO INSTALL, COPY
OR USE ANY OF THE OTHER SOFTWARE COMPONENTS, UNLESS OTHERWISE PROVIDED IN A
DIFFERENT PARAGRAPH OF THIS SECTION.
4.4.1	General. The Windows 95 Software Components are deemed part of Microsoft
Windows 95 (the ["SOFTWARE PRODUCT"]), and are therefore subject to the terms
and conditions of its EULA, except as otherwise provided herein.
4.4.2	Use Limitation. At any point in time, a maximum of ten (10) computers are
permitted to use the services of the Microsoft Personal Web Server component. 
The ten (10) computer maximum includes any indirect uses made through software
or hardware which pools or aggregates uses. The Microsoft Transaction Server for
Windows 95 component may not be used as a network server; that is, no computers
or workstations may access or utilize any network services of that component.
Performance or Benchmark Testing. You may not disclose the results of any
benchmark test of either of the Windows 95 Software Components to any third
party without Microsoft's prior written approval.
4.4.3	Additional Rights and Restrictions. You also have the right to make
additional copies of the Windows 95 Software Components equal to the number of
validly licensed copies of Microsoft Windows 95 which you have, and you may use
each copy in the manner specified above.  If you do not have a valid license for
Microsoft Windows 95, you have no rights under the foregoing section.

5.	DESCRIPTION OF OTHER RIGHTS AND LIMITATIONS
5.1	Not For Resale Software. If the SOFTWARE PRODUCT is labeled "Not For Resale"
or "NFR," then you may not resell, or otherwise transfer for value, the SOFTWARE
PRODUCT.
5.2	Limitations on Reverse Engineering, Decompilation, and Disassembly. You may
not reverse engineer, decompile, or disassemble the SOFTWARE PRODUCT, except and
only to the extent that such activity is expressly permitted by applicable law
notwithstanding this limitation.
5.3	Rental. You may not rent, lease, or lend the SOFTWARE PRODUCT.
5.4	Trademarks. This EULA does not grant you any rights in connection with any
trademarks or service marks of Microsoft.
5.5	Support Services. Microsoft may provide you with support services related to
the SOFTWARE PRODUCT ("Support Services"). Use of Support Services is governed
by the Microsoft policies and programs described in the user manual, in "online"
documentation 
and/or in other Microsoft-provided materials. Any supplemental software code
provided to you as part of the Support Services shall be considered part of the
SOFTWARE PRODUCT and subject to the terms and conditions of this EULA. With
respect to technical information you provide to Microsoft as part of the Support
Services, Microsoft may use such information for its business purposes,
including for product support and development. Microsoft will not utilize such
technical information in a form that personally identifies you.
5.6	Software Transfer. The initial user of the SOFTWARE PRODUCT may make a
one-time permanent transfer of this EULA and SOFTWARE PRODUCT only directly to
an end user. This transfer must include all of the SOFTWARE PRODUCT (including
all component parts, the media and printed materials, any upgrades, this EULA,
and, if applicable, the Certificate of Authenticity). Such transfer may not be
by way of consignment or any other indirect transfer. The transferee of such
one-time transfer must agree to comply with the terms of this EULA, including
the obligation not to further transfer this EULA and SOFTWARE PRODUCT.
5.7	Separation of Components. The SOFTWARE PRODUCT is licensed as a single
product. Its component parts may not be separated for use by more than one user. 
5.8	Termination. Without prejudice to any other rights, Microsoft may terminate
this EULA if you fail to comply with the terms and conditions of this EULA. In
such event, you must destroy all copies of the SOFTWARE PRODUCT and all of its
component parts.

6.	PRERELEASE CODE. Portions of the SOFTWARE PRODUCT may be identified as
prerelease code ("Prerelease Code"). Such Prerelease Code is not at the level of
performance and compatibility of the final, generally available product
offering. The Prerelease Code may not operate correctly and may be substantially
modified prior to first commercial shipment. Microsoft is not obligated to make
this or any later version of the Prerelease Code commercially available. The
grant of license to use Prerelease Code expires upon availability of a
commercial release of the Prerelease Code from Microsoft. NOTE: In the event
that Prerelease Code contains a separate end-user license agreement, the terms
and conditions of such end-user license agreement shall govern your use of the
corresponding Prerelease Code.

7.	UPGRADES. If the SOFTWARE PRODUCT is labeled as an upgrade, you must be
properly licensed to use a product identified by Microsoft as being eligible for
the upgrade in order to use the SOFTWARE PRODUCT. A SOFTWARE PRODUCT labeled as
an upgrade replaces and/or supplements the product that formed the basis for
your eligibility for the upgrade. You may use the resulting upgraded product
only in accordance with the terms of this EULA. If the SOFTWARE PRODUCT is an
upgrade of a component of a package of software programs that you licensed as a
single product, the SOFTWARE PRODUCT may be used and transferred only as part of
that single product package and may not be separated for use on more than one
computer.

8.	COPYRIGHT. All title and intellectual property rights in and to the SOFTWARE
PRODUCT (including but not limited to any images, photographs, animations,
video, audio, music, text, and "applets" incorporated into the SOFTWARE
PRODUCT), the accompanying printed materials, and any copies of the SOFTWARE
PRODUCT are owned by Microsoft or its suppliers. All title and intellectual
property rights in and to the content which may be accessed through use of the
SOFTWARE PRODUCT is the property of the respective content owner and may be
protected by applicable copyright or other intellectual property laws and
treaties. This EULA grants you no rights to use such content. All rights not
expressly granted are reserved by Microsoft.

9.	U.S. GOVERNMENT RESTRICTED RIGHTS. The SOFTWARE PRODUCT and documentation are
provided with RESTRICTED RIGHTS. Use, duplication, or disclosure by the
Government is subject to restrictions as set forth in subparagraph (c)(1)(ii) of
the Rights in Technical Data and Computer Software clause at DFARS 252.227-7013
or subparagraphs (c)(1) and (2) of the Commercial Computer Software-Restricted
Rights at 58 CFR 52.227-19, as applicable. Manufacturer is Microsoft
Corporation/One Microsoft Way/Redmond, WA 98052-6399.

10.	EXPORT RESTRICTIONS. You agree that you will not export or re-export the
SOFTWARE PRODUCT, any part thereof, or any process or service that is the direct
product of the SOFTWARE PRODUCT (the foregoing collectively referred to as the
"Restricted Components"), to any country, person, entity or end user subject to
U.S. export restrictions. You specifically agree not to export or re-export any
of the Restricted Components (i) to any country to which the U.S. has embargoed
or restricted the export of goods or services, which currently include, but are
not necessarily limited to Cuba, Iran, Iraq, Libya, North Korea, Sudan and
Syria, or to any national of any such country, wherever located, who intends to
transmit or transport the Restricted Components back to such country; (ii) to
any end-user who you know or have reason to know will utilize the Restricted
Components in the design, development or production of nuclear, chemical or
biological weapons; or (iii) to any end-user who has been prohibited from
participating in U.S. export transactions by any federal agency of the U.S.
government. You warrant and represent that neither the BXA nor any other U.S.
federal agency has suspended, revoked, or denied your export privileges.

11.	NOTE ON JAVA SUPPORT. THE SOFTWARE PRODUCT CONTAINS SUPPORT FOR PROGRAMS
WRITTEN IN JAVA. JAVA TECHNOLOGY IS NOT FAULT TOLERANT AND IS NOT DESIGNED,
MANUFACTURED, OR INTENDED FOR USE OR RESALE AS ONLINE CONTROL EQUIPMENT IN
HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE PERFORMANCE, SUCH AS IN THE OPERATION
OF NUCLEAR FACILITIES, AIRCRAFT NAVIGATION OR COMMUNICATIONS SYSTEMS, AIR
TRAFFIC CONTROL, DIRECT LIFE SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE
FAILURE OF JAVA TECHNOLOGY COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR
SEVERE PHYSICAL OR ENVIRONMENTAL DAMAGE.
MISCELLANEOUS
If you acquired this product in the United States, this EULA is governed by the
laws of the State of Washington. 
If you acquired this product in Canada, this EULA is governed by the laws of the
Province of Ontario, Canada. Each of the parties hereto irrevocably attorns to
the jurisdiction of the courts of the Province of Ontario and further agrees to
commence any litigation which may arise hereunder in the courts located in the
Judicial District of York, Province of Ontario. 
If this product was acquired outside the United States, then local law may apply.
Should you have any questions concerning this EULA, or if you desire to contact
Microsoft for any reason, please contact Microsoft, or write: Microsoft Sales
Information Center/One Microsoft Way/Redmond, WA 98052-6399. 

LIMITED WARRANTY
LIMITED WARRANTY. Except with respect to the REDISTRIBUTABLES, which are
provided "as is," without warranty of any kind,  Microsoft warrants that (a) the
SOFTWARE PRODUCT will perform substantially in accordance with the accompanying
written materials for a period of ninety (90) days from the date of receipt, and
(b) any Support Services provided by Microsoft shall be substantially as
described in applicable written materials provided to you by Microsoft, and
Microsoft support engineers will make commercially reasonable efforts to solve
any problem. To the extent allowed by applicable law, implied warranties on the
SOFTWARE PRODUCT, if any, are limited to ninety (90) days. Some
states/jurisdictions do not allow limitations on duration of an implied
warranty, so the above limitation may not apply to you.

CUSTOMER REMEDIES. Microsoft's and its suppliers' entire liability and your
exclusive remedy shall be, at Microsoft's option, either (a) return of the price
paid, if any, or (b) repair or replacement of the SOFTWARE PRODUCT that does not
meet Microsoft's Limited Warranty and that is returned to Microsoft with a copy
of your receipt. This Limited Warranty is void if failure of the SOFTWARE
PRODUCT has resulted from accident, abuse, or misapplication. Any replacement
SOFTWARE PRODUCT will be warranted for the remainder of the original warranty
period or thirty (30) days, whichever is longer. Outside the United States,
neither these remedies nor any product support services offered by Microsoft are
available without proof of purchase from an authorized international source.

NO OTHER WARRANTIES. TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW,
MICROSOFT AND ITS SUPPLIERS DISCLAIM ALL OTHER WARRANTIES AND CONDITIONS, EITHER
EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, IMPLIED WARRANTIES OR
CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, TITLE AND
NON-INFRINGEMENT, WITH REGARD TO THE SOFTWARE PRODUCT, AND THE PROVISION OF OR
FAILURE TO PROVIDE SUPPORT SERVICES. THIS LIMITED WARRANTY GIVES YOU SPECIFIC
LEGAL RIGHTS. YOU MAY HAVE OTHERS, WHICH VARY FROM STATE/JURISDICTION TO
STATE/JURISDICTION.

LIMITATION OF LIABILITY. TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW, IN
NO EVENT SHALL MICROSOFT OR ITS SUPPLIERS BE LIABLE FOR ANY SPECIAL, INCIDENTAL,
INDIRECT, OR CONSEQUENTIAL DAMAGES WHATSOEVER (INCLUDING, WITHOUT LIMITATION,
DAMAGES FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, LOSS OF BUSINESS
INFORMATION, OR ANY OTHER PECUNIARY LOSS) ARISING OUT OF THE USE OF OR INABILITY
TO USE THE SOFTWARE PRODUCT OR THE FAILURE TO PROVIDE SUPPORT SERVICES, EVEN IF
MICROSOFT HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. IN ANY CASE,
MICROSOFT'S ENTIRE LIABILITY UNDER ANY PROVISION OF THIS EULA SHALL BE LIMITED
TO THE GREATER OF THE AMOUNT ACTUALLY PAID BY YOU FOR THE SOFTWARE PRODUCT OR
U.S.$5.00; PROVIDED, HOWEVER, IF YOU HAVE ENTERED INTO A MICROSOFT SUPPORT
SERVICES AGREEMENT, MICROSOFT'S ENTIRE LIABILITY REGARDING SUPPORT SERVICES
SHALL BE GOVERNED BY THE TERMS OF THAT AGREEMENT. BECAUSE SOME
STATES/JURISDICTIONS DO NOT ALLOW THE EXCLUSION OR LIMITATION OF LIABILITY, THE
ABOVE LIMITATION MAY NOT APPLY TO YOU.


Si vous avez acquis votre produit Microsoft au CANADA, la garantie
limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe
suivante vous concerne :

GARANTIE LIMITEE
GARANTIE LIMITýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýE - Sauf pour celles du
REDISTRIBUTABLES, qui sont
fournies
"comme telles," sans acune garantie quelle qu'elle soit,  Microsoft garantit que
(a) la performance du LOGICIEL sera substantiellement en
conformitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý avec la
documentation qui accompagne le LOGICIEL, pour une
pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriode de
quatre-vingt-dix
(90) jours ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý compter de la date de
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýception; et
(b) tout support
technique
fourni par Microsoft sera substantiellement en
conformitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý avec toute
documentation affýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýrente fournie par Microsoft et
que les membres
du support
technique de Microsoft feront des efforts raisonnables pour
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsoudre toute
difficultýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý technique
dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýcoulant de l'utilisation du
LOGICIEL. Certaines
juridictions ne permettent pas de limiter dans le temps l'application de la
prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente garantie. Aussi, la limite
stipulýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe
ci-haut pourrait ne pas
s'appliquer
dans votre cas. Dans la mesure permise par la loi, toute garantie implicite
portant sur le LOGICIEL, le cas
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýchýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýant, est
limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý une pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriode de
quatre-vingt-dix (90) jours.

RECOURS DU CLIENT - La seule obligation de Microsoft et de ses fournisseurs et
votre recours exclusif seront, au choix de Microsoft, soit (a) le remboursement
du prix payýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý, si applicable, ou (b) la
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýparation
ou le
remplacement du
LOGICIEL
qui n'est pas conforme ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý la Garantie
Limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe de
Microsoft et qui est
retournýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý
Microsoft avec une copie de votre reýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýu. Cette
Garantie
Limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe est
nulle si le
dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýfaut du LOGICIEL est
causýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý par un accident, un
traitement abusif
ou une
mauvaise application. Tout LOGICIEL de remplacement sera garanti pour le reste
de la pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriode de garantie initiale ou pour trente
(30) jours,
selon la plus
longue de ces pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriodes. A
l'extýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýrieur des
Etats-Unis, aucun de ces
recours non
plus que le support technique offert par Microsoft ne sont disponibles sans une
preuve d'achat provenant d'une source authorisýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe. 

AUCUNE AUTRE GARANTIE - DANS LA MESURE PREVUE PAR LA LOI, MICROSOFT ET SES
FOURNISSEURS EXCLUENT TOUTE AUTRE GARANTIE OU CONDITION, EXPRESSE OU IMPLICITE,
Y COMPRIS MAIS NE SE LIMITANT PAS AUX GARANTIES OU CONDITIONS IMPLICITES DU
CARACTERE ADEQUAT POUR LA COMMERCIALISATION OU UN USAGE PARTICULIER EN CE QUI
CONCERNE LE LOGICIEL OU CONCERNANT LE TITRE, L'ABSENCE DE
CONTREFAýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýON DUDIT
LOGICIEL, ET TOUTE DOCUMENTATION ECRITE QUI L'ACCOMPAGNE, AINSI QUE POUR TOUTE
DISPOSITION CONCERNANT LE SUPORT TECHNIQUE OU LA
FAýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýON DONT
CELUI-CI A ETE
RENDU. CETTE GARANTIE LIMITEE VOUS ACCORDE DES DROITS JURIDIQUES SPECIFIQUES. 

PAS DE RESPONSABILITE POUR LES DOMMAGES INDIRECTS - MICROSOFT OU SES
FOURNISSEURS NE SERONT PAS RESPONSABLES, EN AUCUNE CIRCONSTANCE, POUR TOUT
DOMMAGE SPECIAL, INCIDENT, INDIRECT, OU CONSEQUENT QUEL QU'IL SOIT (Y COMPRIS,
SANS LIMITATION, LES DOMMAGES ENTRAINES PAR LA PERTE DE BENEFICES,
L'INTERRUPTION DES ACTIVITES, LA PERTE D'INFORMATION OU TOUTE AUTRE PERTE
PECUNIAIRE) DECOULANT DE OU RELIE A LA LICENCE D'ACCES DU CLIENTET CE, MEME SI
MICROSOFT A ETE AVISEE DE LA POSSIBILITE DE TELS DOMMAGES. LA RESPONSABILITE DE
MICROSOFT EN VERTU DE TOUTE DISPOSITION DE CETTE CONVENTION NE POURRA EN AUCUN
TEMPS EXCEDER LE PLUS ELEVE ENTRE I) LE MONTANT EFFECTIVEMENT PAYE PAR VOUS POUR
LA LICENCE D'ACCES DU CLIENT OU II) U.S.$5.00. ADVENANT QUE VOUS AYEZ CONTRACTE
PAR ENTENTE DISTINCTE AVEC MICROSOFT POUR UN SUPPORT TECHNIQUE ETENDU, VOUS
SEREZ LIE PAR LES TERMES D' UNE TELLE ENTENTE.

La prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente Convention est
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýgie par les lois en
vigeur dans ela
province
d'Ontario, Canada. Chacune des parties ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý la
prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente reconnaýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýt
irrýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýývocablement
la compýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýtence des tribunaux de la province
d'Ontario et consent
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý
instituer tout
litige qui pourrait dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýcouler de la
prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente
auprýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýs des
tribunaux
situýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýs dans le
district judiciaire de York, province d'Ontario.

Au cas oýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý vous auriez des questions concernant
cette licence ou
que vous
dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsiriez vous mettre en rapport avec Microsoft
pour quelque
raison que ce
soit,
veuillez contacter la succursale Microsoft desservant votre pays, dont l'adresse
est fournie dans ce produit, ou ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýcrire
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý:
Microsoft Sales
Information Center, 
One Microsoft Way, Redmond, Washington 98052-6399.

***************************************************************************

%%The following software may be included in this product:
msvcp80.dll from Microsoft C++

Use of any of this software is governed by the terms of the license below:

END-USER LICENSE AGREEMENT FOR MICROSOFT SOFTWARE

IMPORTANT-READ CAREFULLY: This Microsoft End-User License Agreement (''EULA'')
is a legal agreement between you (either an individual or a single entity) and
Microsoft Corporation for the Microsoft software product(s) accompanying this
EULA, which include(s) computer software and may include "online" or electronic
documentation, associated media, and printed materials (''SOFTWARE PRODUCT'').
By installing, copying, or otherwise using the SOFTWARE PRODUCT or any UPDATES
(as defined below), you agree to be bound by the terms of this EULA. If you do
not agree to the terms of this EULA, do not install, copy, or otherwise use the
SOFTWARE PRODUCT; you may, however, return it to your place of purchase for a
full refund. In addition, by installing, copying, or otherwise using any updates
or other components of the SOFTWARE PRODUCT that you receive separately as part
of the SOFTWARE PRODUCT (''UPDATES''), you agree to be bound by any additional
license terms that accompany such UPDATES. If you do not agree to the additional
license terms that accompany such UPDATES, you may not install, copy, or
otherwise use such UPDATES.

SOFTWARE PRODUCT LICENSE
The SOFTWARE PRODUCT is protected by copyright laws and international copyright
treaties, as well as other intellectual property laws and treaties. The SOFTWARE
PRODUCT is licensed, not sold. NOTE: The terms of a printed, paper EULA which
may accompany the SOFTWARE PRODUCT supersede the terms of any on-screen EULA
found within the SOFTWARE PRODUCT.

1.	LICENSE TO USE SOFTWARE PRODUCT. 
1.1	General License Grant. Microsoft grants to you as an individual, a personal,
nonexclusive license to make and use copies of the SOFTWARE PRODUCT for the sole
purposes of designing, developing, and testing your software product(s) that are
designed to operate in conjunction with any Microsoft operating system product.
You may install copies of the SOFTWARE PRODUCT on an unlimited number of
computers provided that you are the only individual using the SOFTWARE PRODUCT.
If you are an entity, Microsoft grants you the right to designate one individual
within your organization to have the sole right to use the SOFTWARE PRODUCT in
the manner provided above.
1.2	Documentation. This EULA grants you, as an individual, a personal,
nonexclusive license to make and use an unlimited number of copies of any
documentation, provided that such copies shall be used only for personal
purposes and are not to be republished or distributed (either in hard copy or
electronic form) beyond the user's premises and with the following exception:
you may use documentation identified in the MSDN Library portion of the SOFTWARE
PRODUCT as the file format specification for Microsoft Word, Microsoft Excel,
Microsoft Access, and/or Microsoft PowerPoint ("File Format Documentation")
solely in connection with your development of software product(s) that operate
in conjunction with Windows or Windows NT that are not general purpose word
processing, spreadsheet, or database management software products or an
integrated work or product suite whose components include one or more general
purpose word processing, spreadsheet, or database management software products.
Note: A product that includes limited word processing, spreadsheet, or database
components along with other components that provide significant and primary
value, such as an accounting product with limited spreadsheet capability, is not
considered to be a "general purpose" product.
1.3	Storage/Network Use. You may also store or install a copy of the SOFTWARE
PRODUCT on a storage device, such as a network server, used only to install or
run the SOFTWARE PRODUCT on computers used by a licensed end user in accordance
with Section 1.1.  A single license for the SOFTWARE PRODUCT may not be shared
or used concurrently by other end users.
1.4	Visual Studio-Effect of EULA. This Section 1.4 also applies if the SOFTWARE
PRODUCT is Microsoft Visual Studio, a suite of development tools and other
software programs (each such tool or software program, a "Component").
Components that you receive as part of the SOFTWARE PRODUCT may include a
separate end-user license agreement (each, a "Component EULA"). Except as
provided in Section 6, in the event of inconsistencies between this EULA and any
Component EULA, the terms of this EULA shall control.
1.5	Microsoft Internet Explorer. You may make and use copies of the Microsoft
Internet Explorer for use on all computers for which you have a validly licensed
copy of Microsoft operating system products. 

2.	REDISTRIBUTABLE CODE-ADDITIONAL LICENSE RIGHTS. In addition to the rights
granted in Section 1, certain portions of the SOFTWARE PRODUCT, as described in
this Section 2, are provided to you with additional license rights provided that
you comply with the terms of 
Section 3.1.
2.1	Sample Code. Microsoft grants you the right to use and modify the source
code version of those portions of the SOFTWARE PRODUCT identified as "Samples"
in REDIST.TXT or elsewhere in the SOFTWARE PRODUCT ("Sample Code") for the sole
purposes of designing, developing, and testing your software product(s), and to
reproduce and distribute the Sample Code, along with any modifications thereof,
only in object code form.
2.2	Redistributable Code-Standard. Microsoft grants you a nonexclusive,
royalty-free right to reproduce and distribute the object code form of any
portion of the SOFTWARE PRODUCT listed in REDIST.TXT ("Redistributable Code").
NOTE: certain Redistributable Code may be subject to the restrictions in Section
2.3 if it is also identified as "Limited Use Redistributable Code."
2.3	Redistributable Code-Limited Use. Provided that you ALSO comply with the
terms of Section 3.1.3, Microsoft grants you a nonexclusive, royalty-free right
to reproduce and distribute the object code form of those portions of the
SOFTWARE PRODUCT listed in REDIST.TXT as Limited Use Redistributable Code
("Limited Use Redistributable Code").
2.4	Visual C++ and Visual Studio-Microsoft Foundation Classes (MFC), Template
Libraries (ATL), and C runtimes (CRTs). If this EULA accompanies Visual C++ or
Visual Studio, then in addition to the rights granted in Section 1, Microsoft
grants you the right to use and modify the source code version of those portions
of the SOFTWARE PRODUCT that are identified as MFC, ATL, or CRTs (collectively,
the "VC Redistributables"), for the sole purposes of designing, developing, and
testing your software product(s). Provided you comply with Section 3.1 and you
rename any files created by you that are included in the Licensed Product
(defined below), Microsoft grants you a nonexclusive, royalty-free right to
reproduce and distribute the object code version of the VC Redistributables,
including any modifications you make. For purposes of this section,
"modifications" shall mean enhancements to the functionality of the VC
Redistributables.

3.	DISTRIBUTION REQUIREMENTS; LICENSE RESTRICTIONS. 
3.1	General. The SOFTWARE PRODUCT may contain up to three categories of
redistributable code, any redistribution of which by you requires compliance
with the following terms. 
3.1.1.	Redistributable Code-Standard. If you are authorized and choose to
redistribute Sample Code, Redistributable Code, and Limited Use Redistributable
Code, (collectively, the "Redistributables") as described in Section 2, you
agree to: (a) distribute the Redistributables in object code form and only in
conjunction with and as a part of a software application product developed by
you using the product accompanying this EULA that adds significant and primary
functionality to the SOFTWARE PRODUCT ("Licensed Product"); (b) not use
Microsoft's name, logo, or trademarks to market the Licensed Product; (c)
include a valid copyright notice on the Licensed Product; (d) indemnify, hold
harmless, and defend Microsoft from and against any claims or lawsuits,
including attorney's fees, that arise or result from the use or distribution of
the Licensed Product; (e) include "Copyright  Microsoft Systems Journal"
in all Microsoft Systems Journal (MSJ) code used within your program(s); (f)
otherwise comply with the terms of this EULA; and (g) agree that Microsoft
reserves all rights not expressly granted. You also agree not to permit further
distribution of the Redistributables by your end users except: (1) you may
permit further redistribution of the Redistributables by your distributors to
your end-user customers if your distributors only distribute the
Redistributables in conjunction with, and as part of, the Licensed Product and
you and your distributors comply with all other terms of this EULA; and (2) in
the manner described in Section 3.1.2. 
3.1.2	Redistributable Code-Extended Use. Visual Basic, Visual C++, Visual J++,
and Visual Studio. If this EULA accompanies any of the Microsoft products listed
in the heading of this subsection, and subject to your compliance with Section
3.1.1, you may permit your end users to reproduce and distribute the object code
form of certain portions of the SOFTWARE PRODUCT (as listed in REDIST.TXT as
"Extended Use Redistributable Code") only in conjunction with and part of a
Licensed Product and/or Web page that adds significant and primary functionality
to the Extended Use Redistributable Code. (NOTE: The foregoing license grant
does not apply to files designated as Dbgrid.ocx and Graph32.ocx). You are
authorized to exercise the foregoing rights provided that: 
(a)	you comply with Section 3.1.1, and 
(b)	your end user agrees to: (a) distribute the Extended Use Redistributable
Code in object code only in conjunction with and as a part of a software
application product developed by them that adds significant and primary
functionality to the Extended Use Redistributable Code; (b) not use Microsoft's
name, logo, or trademarks to market the End-User Application; (c) include a
valid copyright notice on the End-User Application; (d) indemnify, hold
harmless, and defend Microsoft from and against any claims or lawsuits,
including attorney's fees, that arise or result from the use or distribution of
the End-User Application; and (e) not permit further distribution of the
Extended Use Redistributable Code by the user of the End-User Application.
3.1.3	Redistributable Code-Limited Use. If you are authorized and choose to
redistribute Limited Use Redistributable Code, in addition to the terms of
Section 3.1.1, you must also comply with the following (as applicable to the
corresponding portions of the SOFTWARE PRODUCT identified in REDIST.TXT as
Limited Use Redistributable Code). 
3.1.3.1	"Jet" Files. If you redistribute the "Jet Files" (as identified in the
SOFTWARE PRODUCT) you agree to comply with the following additional
requirements: (a) your Licensed Product shall not substantially duplicate the
capabilities of Microsoft Access or, in the reasonable opinion of Microsoft,
compete with same; and (b) unless your Licensed Product requires your customers
to license Microsoft Access in order to operate, you shall not reproduce or use
any of the Jet Files for commercial distribution in conjunction with a general
purpose word processing, spreadsheet or database management software product, or
an integrated work or product suite whose components include a general purpose
word processing, spreadsheet, or database management software product except for
the exclusive use of importing data to the various formats supported by
Microsoft Access. Note: A product that includes limited word processing,
spreadsheet or database components along with other components which provide
significant and primary value, such as an accounting product with limited
spreadsheet capability, is not considered to be a "general purpose" product. 
3.1.3.2	Microsoft Data Access Components. If you redistribute the Microsoft Data
Access Component file identified as MDAC_TYP.EXE, you also agree to redistribute
such file in object code only in conjunction with and as a part of a Licensed
Product developed by you with a Microsoft development tool product that adds
significant and primary functionality to MDAC_TYP.EXE.

4.	MICROSOFT WINDOWS NT OPTION PACK COMPONENTS.  Notwithstanding anything to the
contrary contained in this EULA, solely for those portions of the SOFTWARE
PRODUCT identified as the Microsoft Windows NT Option Pack Components, the
following provisions apply.  Note that your use of the Microsoft Windows NT
Option Pack Components is (a) subject to your prior acquisition of a validly
licensed copy of certain Microsoft operating system or server products; and (b)
all capitalized terms in this Section  4 refer to those terms as defined in the
end user-license agreement for the Windows NT Option Pack Component referenced
in the respective paragraphs of this Section (all such terms are noted in brackets):
4.1	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT WINDOWS NT SERVER
4.0, MICROSOFT WINDOWS NT SERVER ENTERPRISE EDITION 4.0 OR MICROSOFT BACKOFFICE
2.5, THE FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT WINDOWS NT SERVER 4.0,
MICROSOFT WINDOWS NT SERVER ENTERPRISE EDITION 4.0, OR MICROSOFT BACKOFFICE 2.5,
YOU ARE NOT AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE THE WINDOWS NT SOFTWARE
COMPONENTS.  FOR PURPOSES OF THIS SECTION 4.1, THE "WINDOWS NT SOFTWARE
COMPONENTS" SHALL MEAN THE FOLLOWING SOFTWARE COMPONENTS:  MICROSOFT MESSAGE
QUEUE SERVER, MICROSOFT TRANSACTION SERVER, MICROSOFT INTERNET INFORMATION
SERVER AND THE INTERNET CONNECTION SERVICES FOR MICROSOFT REMOTE ACCESS SERVICE.
 EVEN IF YOU HAVE A RIGHT TO USE THE WINDOWS NT SOFTWARE COMPONENTS, YOU DO NOT
HAVE ANY RIGHT TO INSTALL, COPY OR OTHERWISE USE ANY OF THE OTHER WINDOWS NT
OPTION PACK COMPONENTS, UNLESS OTHERWISE PROVIDED IN A DIFFERENT PARAGRAPH OF
THIS SECTION.
4.1.1	General.  The Windows NT Software Components contain server software and
client software which are deemed part of the [Server Software] and [Client
Software], respectively, of Microsoft Windows NT Server 4.0 (either as a
standalone product or as a component of Microsoft BackOffice) or Microsoft
Windows NT Server, Enterprise Edition 4.0, as applicable.  If you have a valid
license for Microsoft Windows NT Server 4.0, Microsoft Windows NT Server
Enterprise Edition 4.0 or Microsoft BackOffice 2.5 (each referred to
individually as a ["SOFTWARE PRODUCT"]), you are authorized to use the Windows
NT Software Components under the terms and conditions of the EULA applicable to
such product, except as set forth herein.  
4.1.2	For Microsoft Windows NT Server-Client Access.  In addition to the [Client
Access] requirements currently set forth in the applicable EULA, you need a
separate [Client Access License] for Windows NT Server in order to access or
otherwise utilize the following Windows NT Server basic network/application
services or [Server Software] components:  Microsoft Message Queue Server
(sending or receiving messages from Microsoft Message Queue Server), Microsoft
Transaction Server (invoking component-based applications managed by Microsoft
Transaction Server), and Remote Access Service (accessing the server from a
remote location through a communications link).  Note:  Remote Access Service
includes the use of Internet Connection Services, including Internet
Authentication Services (validation or transference of a remote access request)
or Connection Point Services (remotely configuring the Microsoft Connection
Manager Client with new phone numbers or other data).  Performance or Benchmark
Testing.  You may not disclose the results of any benchmark test of either the
[Server Software] or [Client Software] for Microsoft Message Queue Server,
Microsoft Transaction Server or Microsoft Internet Information Server to any
third party without Microsoft's prior written approval.  Installation on a
Single [Server].  The [Server Software] components that make up the applicable
[SOFTWARE PRODUCT] may only be installed together for use on one [Server] and
may not be separated, unless otherwise provided herein.  Note on Microsoft Site
Server Express.  You may freely copy and distribute Microsoft Site Server
Express for your use on any computer within your organization.
4.1.3	For Microsoft Internet Information Server-Use. Notwithstanding anything to
the contrary contained in the applicable EULA, you do not need a separate
[Client Access License] to access or otherwise utilize the services of Microsoft
Internet Information Server, except to the extent that a [Server] or [Server
Software] component which requires a [Client Access License] is accessed or
utilized by Microsoft Internet Information Server.
4.1.4	Additional Rights and Restrictions. You also have the right to make
additional copies of the Windows NT Software Components equal to the number of
validly licensed copies of each [SOFTWARE PRODUCT] which you have, and you may
use each copy in the manner specified above.  If you do not have a valid license
for Microsoft Windows NT Server 4.0, Microsoft Windows NT Server Enterprise
Edition 4.0 or Microsoft BackOffice 2.5, you have no rights under the foregoing
section.  
4.2	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT WINDOWS NT
WORKSTATION 4.0, THE FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT WINDOWS NT WORKSTATION
4.0, YOU ARE NOT AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE THE WINDOWS NT
WORKSTATION SOFTWARE COMPONENTS.  FOR PURPOSES OF THIS SECTION 4.2, THE "WINDOWS
NT WORKSTATION SOFTWARE 
COMPONENTS" SHALL MEAN THE FOLLOWING SOFTWARE COMPONENTS:  MICROSOFT TRANSACTION
SERVER AND MICROSOFT PERSONAL WEB SERVER.  EVEN IF YOU HAVE A RIGHT TO USE THE
WINDOWS NT WORKSTATION SOFTWARE COMPONENTS, YOU DO NOT HAVE ANY RIGHT TO
INSTALL, COPY OR USE ANY OF THE OTHER SOFTWARE COMPONENTS, UNLESS OTHERWISE
PROVIDED IN A DIFFERENT PARAGRAPH OF THIS SECTION.
4.2.1	General. The Windows NT Workstation Software Components are deemed part of
Microsoft Windows NT Workstation 4.0 (the ["SOFTWARE PRODUCT"]), and are
therefore subject to the terms and conditions of its EULA, except as otherwise
provided herein.  Use Limitation. At any point in time, only a maximum of two
(2) computers (instead of ten (10) are permitted to use the services of the
Microsoft Transaction Server component.  The two (2) computer maximum includes
any indirect uses made through software or hardware which pools or aggregates
uses.  Performance or Benchmark Testing. You may not disclose the results of any
benchmark test of either of the Windows NT Workstation Software Components to
any third party without Microsoft's prior written approval.
4.2.2	Additional Rights and Restrictions.  You also have the right to make
additional copies of the Windows NT Workstation Software Components equal to the
number of validly licensed copies of Microsoft Windows NT Workstation 4.0 which
you have, and you may use each copy in the manner specified above.  If you do
not have a valid license for Microsoft Windows NT Workstation 4.0, you have no
rights under the foregoing section.
4.3	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT BACKOFFICE SMALL
BUSINESS SERVER 4.0, THE FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT BACKOFFICE SMALL
BUSINESS SERVER 4.0, YOU ARE NOT AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE
THE WINDOWS NT SOFTWARE COMPONENTS (AS DEFINED PREVIOUSLY IN SECTION 4.1).  EVEN
IF YOU HAVE THE RIGHT TO USE THE WINDOWS NT SOFTWARE COMPONENTS, YOU DO NOT HAVE
ANY RIGHT TO INSTALL, COPY OR OTHERWISE USE ANY OF THE OTHER SOFTWARE
COMPONENTS, UNLESS OTHERWISE PROVIDED IN A DIFFERENT PARAGRAPH OF THIS SECTION.
4.3.1	General. The Windows NT Software Components contain server software and
client software which is deemed part of the [Server Software] and [Client
Software], respectively, of Microsoft BackOffice Small Business Server 4.0, and
is therefore subject to the terms and conditions of its EULA, except as
otherwise provided herein.  Note on Microsoft Site Server Express. You may
freely copy and distribute Microsoft Site Server Express for your use on any
computer within your organization.
4.3.2	Additional Rights and Restrictions. You also have the right to make
additional copies of the Windows NT Software Components equal to the number of
validly licensed copies of Microsoft BackOffice Small Business Server 4.0 which
you have, and you may use each copy in the manner specified above.  If you do
not have a valid license for Microsoft BackOffice Small Business Server 4.0, you
have no rights under the foregoing section.
4.4	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT WINDOWS 95, THE
FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT WINDOWS 95, YOU ARE NOT
AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE THE WINDOWS 95 SOFTWARE COMPONENTS.
 FOR PURPOSES OF THIS SECTION 4.4, THE "WINDOWS 95 SOFTWARE COMPONENTS" SHALL
MEAN THE FOLLOWING SOFTWARE COMPONENTS:  MICROSOFT PERSONAL WEB SERVER AND
MICROSOFT TRANSACTION SERVER FOR WINDOWS 95.  EVEN IF YOU HAVE A RIGHT TO USE
THE WINDOWS 95 SOFTWARE COMPONENTS, YOU DO NOT HAVE ANY RIGHT TO INSTALL, COPY
OR USE ANY OF THE OTHER SOFTWARE COMPONENTS, UNLESS OTHERWISE PROVIDED IN A
DIFFERENT PARAGRAPH OF THIS SECTION.
4.4.1	General. The Windows 95 Software Components are deemed part of Microsoft
Windows 95 (the ["SOFTWARE PRODUCT"]), and are therefore subject to the terms
and conditions of its EULA, except as otherwise provided herein.
4.4.2	Use Limitation. At any point in time, a maximum of ten (10) computers are
permitted to use the services of the Microsoft Personal Web Server component. 
The ten (10) computer maximum includes any indirect uses made through software
or hardware which pools or aggregates uses. The Microsoft Transaction Server for
Windows 95 component may not be used as a network server; that is, no computers
or workstations may access or utilize any network services of that component.
Performance or Benchmark Testing. You may not disclose the results of any
benchmark test of either of the Windows 95 Software Components to any third
party without Microsoft's prior written approval.
4.4.3	Additional Rights and Restrictions. You also have the right to make
additional copies of the Windows 95 Software Components equal to the number of
validly licensed copies of Microsoft Windows 95 which you have, and you may use
each copy in the manner specified above.  If you do not have a valid license for
Microsoft Windows 95, you have no rights under the foregoing section.

5.	DESCRIPTION OF OTHER RIGHTS AND LIMITATIONS
5.1	Not For Resale Software. If the SOFTWARE PRODUCT is labeled "Not For Resale"
or "NFR," then you may not resell, or otherwise transfer for value, the SOFTWARE
PRODUCT.
5.2	Limitations on Reverse Engineering, Decompilation, and Disassembly. You may
not reverse engineer, decompile, or disassemble the SOFTWARE PRODUCT, except and
only to the extent that such activity is expressly permitted by applicable law
notwithstanding this limitation.
5.3	Rental. You may not rent, lease, or lend the SOFTWARE PRODUCT.
5.4	Trademarks. This EULA does not grant you any rights in connection with any
trademarks or service marks of Microsoft.
5.5	Support Services. Microsoft may provide you with support services related to
the SOFTWARE PRODUCT ("Support Services"). Use of Support Services is governed
by the Microsoft policies and programs described in the user manual, in "online"
documentation 
and/or in other Microsoft-provided materials. Any supplemental software code
provided to you as part of the Support Services shall be considered part of the
SOFTWARE PRODUCT and subject to the terms and conditions of this EULA. With
respect to technical information you provide to Microsoft as part of the Support
Services, Microsoft may use such information for its business purposes,
including for product support and development. Microsoft will not utilize such
technical information in a form that personally identifies you.
5.6	Software Transfer. The initial user of the SOFTWARE PRODUCT may make a
one-time permanent transfer of this EULA and SOFTWARE PRODUCT only directly to
an end user. This transfer must include all of the SOFTWARE PRODUCT (including
all component parts, the media and printed materials, any upgrades, this EULA,
and, if applicable, the Certificate of Authenticity). Such transfer may not be
by way of consignment or any other indirect transfer. The transferee of such
one-time transfer must agree to comply with the terms of this EULA, including
the obligation not to further transfer this EULA and SOFTWARE PRODUCT.
5.7	Separation of Components. The SOFTWARE PRODUCT is licensed as a single
product. Its component parts may not be separated for use by more than one user. 
5.8	Termination. Without prejudice to any other rights, Microsoft may terminate
this EULA if you fail to comply with the terms and conditions of this EULA. In
such event, you must destroy all copies of the SOFTWARE PRODUCT and all of its
component parts.

6.	PRERELEASE CODE. Portions of the SOFTWARE PRODUCT may be identified as
prerelease code ("Prerelease Code"). Such Prerelease Code is not at the level of
performance and compatibility of the final, generally available product
offering. The Prerelease Code may not operate correctly and may be substantially
modified prior to first commercial shipment. Microsoft is not obligated to make
this or any later version of the Prerelease Code commercially available. The
grant of license to use Prerelease Code expires upon availability of a
commercial release of the Prerelease Code from Microsoft. NOTE: In the event
that Prerelease Code contains a separate end-user license agreement, the terms
and conditions of such end-user license agreement shall govern your use of the
corresponding Prerelease Code.

7.	UPGRADES. If the SOFTWARE PRODUCT is labeled as an upgrade, you must be
properly licensed to use a product identified by Microsoft as being eligible for
the upgrade in order to use the SOFTWARE PRODUCT. A SOFTWARE PRODUCT labeled as
an upgrade replaces and/or supplements the product that formed the basis for
your eligibility for the upgrade. You may use the resulting upgraded product
only in accordance with the terms of this EULA. If the SOFTWARE PRODUCT is an
upgrade of a component of a package of software programs that you licensed as a
single product, the SOFTWARE PRODUCT may be used and transferred only as part of
that single product package and may not be separated for use on more than one
computer.

8.	COPYRIGHT. All title and intellectual property rights in and to the SOFTWARE
PRODUCT (including but not limited to any images, photographs, animations,
video, audio, music, text, and "applets" incorporated into the SOFTWARE
PRODUCT), the accompanying printed materials, and any copies of the SOFTWARE
PRODUCT are owned by Microsoft or its suppliers. All title and intellectual
property rights in and to the content which may be accessed through use of the
SOFTWARE PRODUCT is the property of the respective content owner and may be
protected by applicable copyright or other intellectual property laws and
treaties. This EULA grants you no rights to use such content. All rights not
expressly granted are reserved by Microsoft.

9.	U.S. GOVERNMENT RESTRICTED RIGHTS. The SOFTWARE PRODUCT and documentation are
provided with RESTRICTED RIGHTS. Use, duplication, or disclosure by the
Government is subject to restrictions as set forth in subparagraph (c)(1)(ii) of
the Rights in Technical Data and Computer Software clause at DFARS 252.227-7013
or subparagraphs (c)(1) and (2) of the Commercial Computer Software-Restricted
Rights at 58 CFR 52.227-19, as applicable. Manufacturer is Microsoft
Corporation/One Microsoft Way/Redmond, WA 98052-6399.

10.	EXPORT RESTRICTIONS. You agree that you will not export or re-export the
SOFTWARE PRODUCT, any part thereof, or any process or service that is the direct
product of the SOFTWARE PRODUCT (the foregoing collectively referred to as the
"Restricted Components"), to any country, person, entity or end user subject to
U.S. export restrictions. You specifically agree not to export or re-export any
of the Restricted Components (i) to any country to which the U.S. has embargoed
or restricted the export of goods or services, which currently include, but are
not necessarily limited to Cuba, Iran, Iraq, Libya, North Korea, Sudan and
Syria, or to any national of any such country, wherever located, who intends to
transmit or transport the Restricted Components back to such country; (ii) to
any end-user who you know or have reason to know will utilize the Restricted
Components in the design, development or production of nuclear, chemical or
biological weapons; or (iii) to any end-user who has been prohibited from
participating in U.S. export transactions by any federal agency of the U.S.
government. You warrant and represent that neither the BXA nor any other U.S.
federal agency has suspended, revoked, or denied your export privileges.

11.	NOTE ON JAVA SUPPORT. THE SOFTWARE PRODUCT CONTAINS SUPPORT FOR PROGRAMS
WRITTEN IN JAVA. JAVA TECHNOLOGY IS NOT FAULT TOLERANT AND IS NOT DESIGNED,
MANUFACTURED, OR INTENDED FOR USE OR RESALE AS ONLINE CONTROL EQUIPMENT IN
HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE PERFORMANCE, SUCH AS IN THE OPERATION
OF NUCLEAR FACILITIES, AIRCRAFT NAVIGATION OR COMMUNICATIONS SYSTEMS, AIR
TRAFFIC CONTROL, DIRECT LIFE SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE
FAILURE OF JAVA TECHNOLOGY COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR
SEVERE PHYSICAL OR ENVIRONMENTAL DAMAGE.
MISCELLANEOUS
If you acquired this product in the United States, this EULA is governed by the
laws of the State of Washington. 
If you acquired this product in Canada, this EULA is governed by the laws of the
Province of Ontario, Canada. Each of the parties hereto irrevocably attorns to
the jurisdiction of the courts of the Province of Ontario and further agrees to
commence any litigation which may arise hereunder in the courts located in the
Judicial District of York, Province of Ontario. 
If this product was acquired outside the United States, then local law may apply.
Should you have any questions concerning this EULA, or if you desire to contact
Microsoft for any reason, please contact Microsoft, or write: Microsoft Sales
Information Center/One Microsoft Way/Redmond, WA 98052-6399. 

LIMITED WARRANTY
LIMITED WARRANTY. Except with respect to the REDISTRIBUTABLES, which are
provided "as is," without warranty of any kind,  Microsoft warrants that (a) the
SOFTWARE PRODUCT will perform substantially in accordance with the accompanying
written materials for a period of ninety (90) days from the date of receipt, and
(b) any Support Services provided by Microsoft shall be substantially as
described in applicable written materials provided to you by Microsoft, and
Microsoft support engineers will make commercially reasonable efforts to solve
any problem. To the extent allowed by applicable law, implied warranties on the
SOFTWARE PRODUCT, if any, are limited to ninety (90) days. Some
states/jurisdictions do not allow limitations on duration of an implied
warranty, so the above limitation may not apply to you.

CUSTOMER REMEDIES. Microsoft's and its suppliers' entire liability and your
exclusive remedy shall be, at Microsoft's option, either (a) return of the price
paid, if any, or (b) repair or replacement of the SOFTWARE PRODUCT that does not
meet Microsoft's Limited Warranty and that is returned to Microsoft with a copy
of your receipt. This Limited Warranty is void if failure of the SOFTWARE
PRODUCT has resulted from accident, abuse, or misapplication. Any replacement
SOFTWARE PRODUCT will be warranted for the remainder of the original warranty
period or thirty (30) days, whichever is longer. Outside the United States,
neither these remedies nor any product support services offered by Microsoft are
available without proof of purchase from an authorized international source.

NO OTHER WARRANTIES. TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW,
MICROSOFT AND ITS SUPPLIERS DISCLAIM ALL OTHER WARRANTIES AND CONDITIONS, EITHER
EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, IMPLIED WARRANTIES OR
CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, TITLE AND
NON-INFRINGEMENT, WITH REGARD TO THE SOFTWARE PRODUCT, AND THE PROVISION OF OR
FAILURE TO PROVIDE SUPPORT SERVICES. THIS LIMITED WARRANTY GIVES YOU SPECIFIC
LEGAL RIGHTS. YOU MAY HAVE OTHERS, WHICH VARY FROM STATE/JURISDICTION TO
STATE/JURISDICTION.

LIMITATION OF LIABILITY. TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW, IN
NO EVENT SHALL MICROSOFT OR ITS SUPPLIERS BE LIABLE FOR ANY SPECIAL, INCIDENTAL,
INDIRECT, OR CONSEQUENTIAL DAMAGES WHATSOEVER (INCLUDING, WITHOUT LIMITATION,
DAMAGES FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, LOSS OF BUSINESS
INFORMATION, OR ANY OTHER PECUNIARY LOSS) ARISING OUT OF THE USE OF OR INABILITY
TO USE THE SOFTWARE PRODUCT OR THE FAILURE TO PROVIDE SUPPORT SERVICES, EVEN IF
MICROSOFT HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. IN ANY CASE,
MICROSOFT'S ENTIRE LIABILITY UNDER ANY PROVISION OF THIS EULA SHALL BE LIMITED
TO THE GREATER OF THE AMOUNT ACTUALLY PAID BY YOU FOR THE SOFTWARE PRODUCT OR
U.S.$5.00; PROVIDED, HOWEVER, IF YOU HAVE ENTERED INTO A MICROSOFT SUPPORT
SERVICES AGREEMENT, MICROSOFT'S ENTIRE LIABILITY REGARDING SUPPORT SERVICES
SHALL BE GOVERNED BY THE TERMS OF THAT AGREEMENT. BECAUSE SOME
STATES/JURISDICTIONS DO NOT ALLOW THE EXCLUSION OR LIMITATION OF LIABILITY, THE
ABOVE LIMITATION MAY NOT APPLY TO YOU.


Si vous avez acquis votre produit Microsoft au CANADA, la garantie
limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe
suivante vous concerne :

GARANTIE LIMITEE
GARANTIE LIMITýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýE - Sauf pour celles du
REDISTRIBUTABLES, qui sont
fournies
"comme telles," sans acune garantie quelle qu'elle soit,  Microsoft garantit que
(a) la performance du LOGICIEL sera substantiellement en
conformitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý avec la
documentation qui accompagne le LOGICIEL, pour une
pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriode de
quatre-vingt-dix
(90) jours ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý compter de la date de
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýception; et
(b) tout support
technique
fourni par Microsoft sera substantiellement en
conformitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý avec toute
documentation affýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýrente fournie par Microsoft et
que les membres
du support
technique de Microsoft feront des efforts raisonnables pour
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsoudre toute
difficultýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý technique
dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýcoulant de l'utilisation du
LOGICIEL. Certaines
juridictions ne permettent pas de limiter dans le temps l'application de la
prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente garantie. Aussi, la limite
stipulýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe
ci-haut pourrait ne pas
s'appliquer
dans votre cas. Dans la mesure permise par la loi, toute garantie implicite
portant sur le LOGICIEL, le cas
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýchýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýant, est
limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý une pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriode de
quatre-vingt-dix (90) jours.

RECOURS DU CLIENT - La seule obligation de Microsoft et de ses fournisseurs et
votre recours exclusif seront, au choix de Microsoft, soit (a) le remboursement
du prix payýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý, si applicable, ou (b) la
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýparation
ou le
remplacement du
LOGICIEL
qui n'est pas conforme ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý la Garantie
Limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe de
Microsoft et qui est
retournýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý
Microsoft avec une copie de votre reýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýu. Cette
Garantie
Limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe est
nulle si le
dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýfaut du LOGICIEL est
causýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý par un accident, un
traitement abusif
ou une
mauvaise application. Tout LOGICIEL de remplacement sera garanti pour le reste
de la pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriode de garantie initiale ou pour trente
(30) jours,
selon la plus
longue de ces pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriodes. A
l'extýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýrieur des
Etats-Unis, aucun de ces
recours non
plus que le support technique offert par Microsoft ne sont disponibles sans une
preuve d'achat provenant d'une source authorisýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe. 

AUCUNE AUTRE GARANTIE - DANS LA MESURE PREVUE PAR LA LOI, MICROSOFT ET SES
FOURNISSEURS EXCLUENT TOUTE AUTRE GARANTIE OU CONDITION, EXPRESSE OU IMPLICITE,
Y COMPRIS MAIS NE SE LIMITANT PAS AUX GARANTIES OU CONDITIONS IMPLICITES DU
CARACTERE ADEQUAT POUR LA COMMERCIALISATION OU UN USAGE PARTICULIER EN CE QUI
CONCERNE LE LOGICIEL OU CONCERNANT LE TITRE, L'ABSENCE DE
CONTREFAýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýON DUDIT
LOGICIEL, ET TOUTE DOCUMENTATION ECRITE QUI L'ACCOMPAGNE, AINSI QUE POUR TOUTE
DISPOSITION CONCERNANT LE SUPORT TECHNIQUE OU LA
FAýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýON DONT
CELUI-CI A ETE
RENDU. CETTE GARANTIE LIMITEE VOUS ACCORDE DES DROITS JURIDIQUES SPECIFIQUES. 

PAS DE RESPONSABILITE POUR LES DOMMAGES INDIRECTS - MICROSOFT OU SES
FOURNISSEURS NE SERONT PAS RESPONSABLES, EN AUCUNE CIRCONSTANCE, POUR TOUT
DOMMAGE SPECIAL, INCIDENT, INDIRECT, OU CONSEQUENT QUEL QU'IL SOIT (Y COMPRIS,
SANS LIMITATION, LES DOMMAGES ENTRAINES PAR LA PERTE DE BENEFICES,
L'INTERRUPTION DES ACTIVITES, LA PERTE D'INFORMATION OU TOUTE AUTRE PERTE
PECUNIAIRE) DECOULANT DE OU RELIE A LA LICENCE D'ACCES DU CLIENTET CE, MEME SI
MICROSOFT A ETE AVISEE DE LA POSSIBILITE DE TELS DOMMAGES. LA RESPONSABILITE DE
MICROSOFT EN VERTU DE TOUTE DISPOSITION DE CETTE CONVENTION NE POURRA EN AUCUN
TEMPS EXCEDER LE PLUS ELEVE ENTRE I) LE MONTANT EFFECTIVEMENT PAYE PAR VOUS POUR
LA LICENCE D'ACCES DU CLIENT OU II) U.S.$5.00. ADVENANT QUE VOUS AYEZ CONTRACTE
PAR ENTENTE DISTINCTE AVEC MICROSOFT POUR UN SUPPORT TECHNIQUE ETENDU, VOUS
SEREZ LIE PAR LES TERMES D' UNE TELLE ENTENTE.

La prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente Convention est
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýgie par les lois en
vigeur dans ela
province
d'Ontario, Canada. Chacune des parties ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý la
prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente reconnaýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýt
irrýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýývocablement
la compýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýtence des tribunaux de la province
d'Ontario et consent
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý
instituer tout
litige qui pourrait dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýcouler de la
prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente
auprýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýs des
tribunaux
situýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýs dans le
district judiciaire de York, province d'Ontario.

Au cas oýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý vous auriez des questions concernant
cette licence ou
que vous
dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsiriez vous mettre en rapport avec Microsoft
pour quelque
raison que ce
soit,
veuillez contacter la succursale Microsoft desservant votre pays, dont l'adresse
est fournie dans ce produit, ou ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýcrire
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý:
Microsoft Sales
Information Center, 
One Microsoft Way, Redmond, Washington 98052-6399.

***************************************************************************

%%The following software may be included in this product:
msvcm80.dll from Microsoft C++

Use of any of this software is governed by the terms of the license below:

END-USER LICENSE AGREEMENT FOR MICROSOFT SOFTWARE

IMPORTANT-READ CAREFULLY: This Microsoft End-User License Agreement (''EULA'')
is a legal agreement between you (either an individual or a single entity) and
Microsoft Corporation for the Microsoft software product(s) accompanying this
EULA, which include(s) computer software and may include "online" or electronic
documentation, associated media, and printed materials (''SOFTWARE PRODUCT'').
By installing, copying, or otherwise using the SOFTWARE PRODUCT or any UPDATES
(as defined below), you agree to be bound by the terms of this EULA. If you do
not agree to the terms of this EULA, do not install, copy, or otherwise use the
SOFTWARE PRODUCT; you may, however, return it to your place of purchase for a
full refund. In addition, by installing, copying, or otherwise using any updates
or other components of the SOFTWARE PRODUCT that you receive separately as part
of the SOFTWARE PRODUCT (''UPDATES''), you agree to be bound by any additional
license terms that accompany such UPDATES. If you do not agree to the additional
license terms that accompany such UPDATES, you may not install, copy, or
otherwise use such UPDATES.

SOFTWARE PRODUCT LICENSE
The SOFTWARE PRODUCT is protected by copyright laws and international copyright
treaties, as well as other intellectual property laws and treaties. The SOFTWARE
PRODUCT is licensed, not sold. NOTE: The terms of a printed, paper EULA which
may accompany the SOFTWARE PRODUCT supersede the terms of any on-screen EULA
found within the SOFTWARE PRODUCT.

1.	LICENSE TO USE SOFTWARE PRODUCT. 
1.1	General License Grant. Microsoft grants to you as an individual, a personal,
nonexclusive license to make and use copies of the SOFTWARE PRODUCT for the sole
purposes of designing, developing, and testing your software product(s) that are
designed to operate in conjunction with any Microsoft operating system product.
You may install copies of the SOFTWARE PRODUCT on an unlimited number of
computers provided that you are the only individual using the SOFTWARE PRODUCT.
If you are an entity, Microsoft grants you the right to designate one individual
within your organization to have the sole right to use the SOFTWARE PRODUCT in
the manner provided above.
1.2	Documentation. This EULA grants you, as an individual, a personal,
nonexclusive license to make and use an unlimited number of copies of any
documentation, provided that such copies shall be used only for personal
purposes and are not to be republished or distributed (either in hard copy or
electronic form) beyond the user's premises and with the following exception:
you may use documentation identified in the MSDN Library portion of the SOFTWARE
PRODUCT as the file format specification for Microsoft Word, Microsoft Excel,
Microsoft Access, and/or Microsoft PowerPoint ("File Format Documentation")
solely in connection with your development of software product(s) that operate
in conjunction with Windows or Windows NT that are not general purpose word
processing, spreadsheet, or database management software products or an
integrated work or product suite whose components include one or more general
purpose word processing, spreadsheet, or database management software products.
Note: A product that includes limited word processing, spreadsheet, or database
components along with other components that provide significant and primary
value, such as an accounting product with limited spreadsheet capability, is not
considered to be a "general purpose" product.
1.3	Storage/Network Use. You may also store or install a copy of the SOFTWARE
PRODUCT on a storage device, such as a network server, used only to install or
run the SOFTWARE PRODUCT on computers used by a licensed end user in accordance
with Section 1.1.  A single license for the SOFTWARE PRODUCT may not be shared
or used concurrently by other end users.
1.4	Visual Studio-Effect of EULA. This Section 1.4 also applies if the SOFTWARE
PRODUCT is Microsoft Visual Studio, a suite of development tools and other
software programs (each such tool or software program, a "Component").
Components that you receive as part of the SOFTWARE PRODUCT may include a
separate end-user license agreement (each, a "Component EULA"). Except as
provided in Section 6, in the event of inconsistencies between this EULA and any
Component EULA, the terms of this EULA shall control.
1.5	Microsoft Internet Explorer. You may make and use copies of the Microsoft
Internet Explorer for use on all computers for which you have a validly licensed
copy of Microsoft operating system products. 

2.	REDISTRIBUTABLE CODE-ADDITIONAL LICENSE RIGHTS. In addition to the rights
granted in Section 1, certain portions of the SOFTWARE PRODUCT, as described in
this Section 2, are provided to you with additional license rights provided that
you comply with the terms of 
Section 3.1.
2.1	Sample Code. Microsoft grants you the right to use and modify the source
code version of those portions of the SOFTWARE PRODUCT identified as "Samples"
in REDIST.TXT or elsewhere in the SOFTWARE PRODUCT ("Sample Code") for the sole
purposes of designing, developing, and testing your software product(s), and to
reproduce and distribute the Sample Code, along with any modifications thereof,
only in object code form.
2.2	Redistributable Code-Standard. Microsoft grants you a nonexclusive,
royalty-free right to reproduce and distribute the object code form of any
portion of the SOFTWARE PRODUCT listed in REDIST.TXT ("Redistributable Code").
NOTE: certain Redistributable Code may be subject to the restrictions in Section
2.3 if it is also identified as "Limited Use Redistributable Code."
2.3	Redistributable Code-Limited Use. Provided that you ALSO comply with the
terms of Section 3.1.3, Microsoft grants you a nonexclusive, royalty-free right
to reproduce and distribute the object code form of those portions of the
SOFTWARE PRODUCT listed in REDIST.TXT as Limited Use Redistributable Code
("Limited Use Redistributable Code").
2.4	Visual C++ and Visual Studio-Microsoft Foundation Classes (MFC), Template
Libraries (ATL), and C runtimes (CRTs). If this EULA accompanies Visual C++ or
Visual Studio, then in addition to the rights granted in Section 1, Microsoft
grants you the right to use and modify the source code version of those portions
of the SOFTWARE PRODUCT that are identified as MFC, ATL, or CRTs (collectively,
the "VC Redistributables"), for the sole purposes of designing, developing, and
testing your software product(s). Provided you comply with Section 3.1 and you
rename any files created by you that are included in the Licensed Product
(defined below), Microsoft grants you a nonexclusive, royalty-free right to
reproduce and distribute the object code version of the VC Redistributables,
including any modifications you make. For purposes of this section,
"modifications" shall mean enhancements to the functionality of the VC
Redistributables.

3.	DISTRIBUTION REQUIREMENTS; LICENSE RESTRICTIONS. 
3.1	General. The SOFTWARE PRODUCT may contain up to three categories of
redistributable code, any redistribution of which by you requires compliance
with the following terms. 
3.1.1.	Redistributable Code-Standard. If you are authorized and choose to
redistribute Sample Code, Redistributable Code, and Limited Use Redistributable
Code, (collectively, the "Redistributables") as described in Section 2, you
agree to: (a) distribute the Redistributables in object code form and only in
conjunction with and as a part of a software application product developed by
you using the product accompanying this EULA that adds significant and primary
functionality to the SOFTWARE PRODUCT ("Licensed Product"); (b) not use
Microsoft's name, logo, or trademarks to market the Licensed Product; (c)
include a valid copyright notice on the Licensed Product; (d) indemnify, hold
harmless, and defend Microsoft from and against any claims or lawsuits,
including attorney's fees, that arise or result from the use or distribution of
the Licensed Product; (e) include "Copyright  Microsoft Systems Journal"
in all Microsoft Systems Journal (MSJ) code used within your program(s); (f)
otherwise comply with the terms of this EULA; and (g) agree that Microsoft
reserves all rights not expressly granted. You also agree not to permit further
distribution of the Redistributables by your end users except: (1) you may
permit further redistribution of the Redistributables by your distributors to
your end-user customers if your distributors only distribute the
Redistributables in conjunction with, and as part of, the Licensed Product and
you and your distributors comply with all other terms of this EULA; and (2) in
the manner described in Section 3.1.2. 
3.1.2	Redistributable Code-Extended Use. Visual Basic, Visual C++, Visual J++,
and Visual Studio. If this EULA accompanies any of the Microsoft products listed
in the heading of this subsection, and subject to your compliance with Section
3.1.1, you may permit your end users to reproduce and distribute the object code
form of certain portions of the SOFTWARE PRODUCT (as listed in REDIST.TXT as
"Extended Use Redistributable Code") only in conjunction with and part of a
Licensed Product and/or Web page that adds significant and primary functionality
to the Extended Use Redistributable Code. (NOTE: The foregoing license grant
does not apply to files designated as Dbgrid.ocx and Graph32.ocx). You are
authorized to exercise the foregoing rights provided that: 
(a)	you comply with Section 3.1.1, and 
(b)	your end user agrees to: (a) distribute the Extended Use Redistributable
Code in object code only in conjunction with and as a part of a software
application product developed by them that adds significant and primary
functionality to the Extended Use Redistributable Code; (b) not use Microsoft's
name, logo, or trademarks to market the End-User Application; (c) include a
valid copyright notice on the End-User Application; (d) indemnify, hold
harmless, and defend Microsoft from and against any claims or lawsuits,
including attorney's fees, that arise or result from the use or distribution of
the End-User Application; and (e) not permit further distribution of the
Extended Use Redistributable Code by the user of the End-User Application.
3.1.3	Redistributable Code-Limited Use. If you are authorized and choose to
redistribute Limited Use Redistributable Code, in addition to the terms of
Section 3.1.1, you must also comply with the following (as applicable to the
corresponding portions of the SOFTWARE PRODUCT identified in REDIST.TXT as
Limited Use Redistributable Code). 
3.1.3.1	"Jet" Files. If you redistribute the "Jet Files" (as identified in the
SOFTWARE PRODUCT) you agree to comply with the following additional
requirements: (a) your Licensed Product shall not substantially duplicate the
capabilities of Microsoft Access or, in the reasonable opinion of Microsoft,
compete with same; and (b) unless your Licensed Product requires your customers
to license Microsoft Access in order to operate, you shall not reproduce or use
any of the Jet Files for commercial distribution in conjunction with a general
purpose word processing, spreadsheet or database management software product, or
an integrated work or product suite whose components include a general purpose
word processing, spreadsheet, or database management software product except for
the exclusive use of importing data to the various formats supported by
Microsoft Access. Note: A product that includes limited word processing,
spreadsheet or database components along with other components which provide
significant and primary value, such as an accounting product with limited
spreadsheet capability, is not considered to be a "general purpose" product. 
3.1.3.2	Microsoft Data Access Components. If you redistribute the Microsoft Data
Access Component file identified as MDAC_TYP.EXE, you also agree to redistribute
such file in object code only in conjunction with and as a part of a Licensed
Product developed by you with a Microsoft development tool product that adds
significant and primary functionality to MDAC_TYP.EXE.

4.	MICROSOFT WINDOWS NT OPTION PACK COMPONENTS.  Notwithstanding anything to the
contrary contained in this EULA, solely for those portions of the SOFTWARE
PRODUCT identified as the Microsoft Windows NT Option Pack Components, the
following provisions apply.  Note that your use of the Microsoft Windows NT
Option Pack Components is (a) subject to your prior acquisition of a validly
licensed copy of certain Microsoft operating system or server products; and (b)
all capitalized terms in this Section  4 refer to those terms as defined in the
end user-license agreement for the Windows NT Option Pack Component referenced
in the respective paragraphs of this Section (all such terms are noted in brackets):
4.1	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT WINDOWS NT SERVER
4.0, MICROSOFT WINDOWS NT SERVER ENTERPRISE EDITION 4.0 OR MICROSOFT BACKOFFICE
2.5, THE FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT WINDOWS NT SERVER 4.0,
MICROSOFT WINDOWS NT SERVER ENTERPRISE EDITION 4.0, OR MICROSOFT BACKOFFICE 2.5,
YOU ARE NOT AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE THE WINDOWS NT SOFTWARE
COMPONENTS.  FOR PURPOSES OF THIS SECTION 4.1, THE "WINDOWS NT SOFTWARE
COMPONENTS" SHALL MEAN THE FOLLOWING SOFTWARE COMPONENTS:  MICROSOFT MESSAGE
QUEUE SERVER, MICROSOFT TRANSACTION SERVER, MICROSOFT INTERNET INFORMATION
SERVER AND THE INTERNET CONNECTION SERVICES FOR MICROSOFT REMOTE ACCESS SERVICE.
 EVEN IF YOU HAVE A RIGHT TO USE THE WINDOWS NT SOFTWARE COMPONENTS, YOU DO NOT
HAVE ANY RIGHT TO INSTALL, COPY OR OTHERWISE USE ANY OF THE OTHER WINDOWS NT
OPTION PACK COMPONENTS, UNLESS OTHERWISE PROVIDED IN A DIFFERENT PARAGRAPH OF
THIS SECTION.
4.1.1	General.  The Windows NT Software Components contain server software and
client software which are deemed part of the [Server Software] and [Client
Software], respectively, of Microsoft Windows NT Server 4.0 (either as a
standalone product or as a component of Microsoft BackOffice) or Microsoft
Windows NT Server, Enterprise Edition 4.0, as applicable.  If you have a valid
license for Microsoft Windows NT Server 4.0, Microsoft Windows NT Server
Enterprise Edition 4.0 or Microsoft BackOffice 2.5 (each referred to
individually as a ["SOFTWARE PRODUCT"]), you are authorized to use the Windows
NT Software Components under the terms and conditions of the EULA applicable to
such product, except as set forth herein.  
4.1.2	For Microsoft Windows NT Server-Client Access.  In addition to the [Client
Access] requirements currently set forth in the applicable EULA, you need a
separate [Client Access License] for Windows NT Server in order to access or
otherwise utilize the following Windows NT Server basic network/application
services or [Server Software] components:  Microsoft Message Queue Server
(sending or receiving messages from Microsoft Message Queue Server), Microsoft
Transaction Server (invoking component-based applications managed by Microsoft
Transaction Server), and Remote Access Service (accessing the server from a
remote location through a communications link).  Note:  Remote Access Service
includes the use of Internet Connection Services, including Internet
Authentication Services (validation or transference of a remote access request)
or Connection Point Services (remotely configuring the Microsoft Connection
Manager Client with new phone numbers or other data).  Performance or Benchmark
Testing.  You may not disclose the results of any benchmark test of either the
[Server Software] or [Client Software] for Microsoft Message Queue Server,
Microsoft Transaction Server or Microsoft Internet Information Server to any
third party without Microsoft's prior written approval.  Installation on a
Single [Server].  The [Server Software] components that make up the applicable
[SOFTWARE PRODUCT] may only be installed together for use on one [Server] and
may not be separated, unless otherwise provided herein.  Note on Microsoft Site
Server Express.  You may freely copy and distribute Microsoft Site Server
Express for your use on any computer within your organization.
4.1.3	For Microsoft Internet Information Server-Use. Notwithstanding anything to
the contrary contained in the applicable EULA, you do not need a separate
[Client Access License] to access or otherwise utilize the services of Microsoft
Internet Information Server, except to the extent that a [Server] or [Server
Software] component which requires a [Client Access License] is accessed or
utilized by Microsoft Internet Information Server.
4.1.4	Additional Rights and Restrictions. You also have the right to make
additional copies of the Windows NT Software Components equal to the number of
validly licensed copies of each [SOFTWARE PRODUCT] which you have, and you may
use each copy in the manner specified above.  If you do not have a valid license
for Microsoft Windows NT Server 4.0, Microsoft Windows NT Server Enterprise
Edition 4.0 or Microsoft BackOffice 2.5, you have no rights under the foregoing
section.  
4.2	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT WINDOWS NT
WORKSTATION 4.0, THE FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT WINDOWS NT WORKSTATION
4.0, YOU ARE NOT AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE THE WINDOWS NT
WORKSTATION SOFTWARE COMPONENTS.  FOR PURPOSES OF THIS SECTION 4.2, THE "WINDOWS
NT WORKSTATION SOFTWARE 
COMPONENTS" SHALL MEAN THE FOLLOWING SOFTWARE COMPONENTS:  MICROSOFT TRANSACTION
SERVER AND MICROSOFT PERSONAL WEB SERVER.  EVEN IF YOU HAVE A RIGHT TO USE THE
WINDOWS NT WORKSTATION SOFTWARE COMPONENTS, YOU DO NOT HAVE ANY RIGHT TO
INSTALL, COPY OR USE ANY OF THE OTHER SOFTWARE COMPONENTS, UNLESS OTHERWISE
PROVIDED IN A DIFFERENT PARAGRAPH OF THIS SECTION.
4.2.1	General. The Windows NT Workstation Software Components are deemed part of
Microsoft Windows NT Workstation 4.0 (the ["SOFTWARE PRODUCT"]), and are
therefore subject to the terms and conditions of its EULA, except as otherwise
provided herein.  Use Limitation. At any point in time, only a maximum of two
(2) computers (instead of ten (10) are permitted to use the services of the
Microsoft Transaction Server component.  The two (2) computer maximum includes
any indirect uses made through software or hardware which pools or aggregates
uses.  Performance or Benchmark Testing. You may not disclose the results of any
benchmark test of either of the Windows NT Workstation Software Components to
any third party without Microsoft's prior written approval.
4.2.2	Additional Rights and Restrictions.  You also have the right to make
additional copies of the Windows NT Workstation Software Components equal to the
number of validly licensed copies of Microsoft Windows NT Workstation 4.0 which
you have, and you may use each copy in the manner specified above.  If you do
not have a valid license for Microsoft Windows NT Workstation 4.0, you have no
rights under the foregoing section.
4.3	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT BACKOFFICE SMALL
BUSINESS SERVER 4.0, THE FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT BACKOFFICE SMALL
BUSINESS SERVER 4.0, YOU ARE NOT AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE
THE WINDOWS NT SOFTWARE COMPONENTS (AS DEFINED PREVIOUSLY IN SECTION 4.1).  EVEN
IF YOU HAVE THE RIGHT TO USE THE WINDOWS NT SOFTWARE COMPONENTS, YOU DO NOT HAVE
ANY RIGHT TO INSTALL, COPY OR OTHERWISE USE ANY OF THE OTHER SOFTWARE
COMPONENTS, UNLESS OTHERWISE PROVIDED IN A DIFFERENT PARAGRAPH OF THIS SECTION.
4.3.1	General. The Windows NT Software Components contain server software and
client software which is deemed part of the [Server Software] and [Client
Software], respectively, of Microsoft BackOffice Small Business Server 4.0, and
is therefore subject to the terms and conditions of its EULA, except as
otherwise provided herein.  Note on Microsoft Site Server Express. You may
freely copy and distribute Microsoft Site Server Express for your use on any
computer within your organization.
4.3.2	Additional Rights and Restrictions. You also have the right to make
additional copies of the Windows NT Software Components equal to the number of
validly licensed copies of Microsoft BackOffice Small Business Server 4.0 which
you have, and you may use each copy in the manner specified above.  If you do
not have a valid license for Microsoft BackOffice Small Business Server 4.0, you
have no rights under the foregoing section.
4.4	IF YOU USE THE SOFTWARE COMPONENTS AS PART OF MICROSOFT WINDOWS 95, THE
FOLLOWING TERMS APPLY TO YOU:
NOTE:  IF YOU DO NOT HAVE A VALID LICENSE FOR MICROSOFT WINDOWS 95, YOU ARE NOT
AUTHORIZED TO INSTALL, COPY OR OTHERWISE USE THE WINDOWS 95 SOFTWARE COMPONENTS.
 FOR PURPOSES OF THIS SECTION 4.4, THE "WINDOWS 95 SOFTWARE COMPONENTS" SHALL
MEAN THE FOLLOWING SOFTWARE COMPONENTS:  MICROSOFT PERSONAL WEB SERVER AND
MICROSOFT TRANSACTION SERVER FOR WINDOWS 95.  EVEN IF YOU HAVE A RIGHT TO USE
THE WINDOWS 95 SOFTWARE COMPONENTS, YOU DO NOT HAVE ANY RIGHT TO INSTALL, COPY
OR USE ANY OF THE OTHER SOFTWARE COMPONENTS, UNLESS OTHERWISE PROVIDED IN A
DIFFERENT PARAGRAPH OF THIS SECTION.
4.4.1	General. The Windows 95 Software Components are deemed part of Microsoft
Windows 95 (the ["SOFTWARE PRODUCT"]), and are therefore subject to the terms
and conditions of its EULA, except as otherwise provided herein.
4.4.2	Use Limitation. At any point in time, a maximum of ten (10) computers are
permitted to use the services of the Microsoft Personal Web Server component. 
The ten (10) computer maximum includes any indirect uses made through software
or hardware which pools or aggregates uses. The Microsoft Transaction Server for
Windows 95 component may not be used as a network server; that is, no computers
or workstations may access or utilize any network services of that component.
Performance or Benchmark Testing. You may not disclose the results of any
benchmark test of either of the Windows 95 Software Components to any third
party without Microsoft's prior written approval.
4.4.3	Additional Rights and Restrictions. You also have the right to make
additional copies of the Windows 95 Software Components equal to the number of
validly licensed copies of Microsoft Windows 95 which you have, and you may use
each copy in the manner specified above.  If you do not have a valid license for
Microsoft Windows 95, you have no rights under the foregoing section.

5.	DESCRIPTION OF OTHER RIGHTS AND LIMITATIONS
5.1	Not For Resale Software. If the SOFTWARE PRODUCT is labeled "Not For Resale"
or "NFR," then you may not resell, or otherwise transfer for value, the SOFTWARE
PRODUCT.
5.2	Limitations on Reverse Engineering, Decompilation, and Disassembly. You may
not reverse engineer, decompile, or disassemble the SOFTWARE PRODUCT, except and
only to the extent that such activity is expressly permitted by applicable law
notwithstanding this limitation.
5.3	Rental. You may not rent, lease, or lend the SOFTWARE PRODUCT.
5.4	Trademarks. This EULA does not grant you any rights in connection with any
trademarks or service marks of Microsoft.
5.5	Support Services. Microsoft may provide you with support services related to
the SOFTWARE PRODUCT ("Support Services"). Use of Support Services is governed
by the Microsoft policies and programs described in the user manual, in "online"
documentation 
and/or in other Microsoft-provided materials. Any supplemental software code
provided to you as part of the Support Services shall be considered part of the
SOFTWARE PRODUCT and subject to the terms and conditions of this EULA. With
respect to technical information you provide to Microsoft as part of the Support
Services, Microsoft may use such information for its business purposes,
including for product support and development. Microsoft will not utilize such
technical information in a form that personally identifies you.
5.6	Software Transfer. The initial user of the SOFTWARE PRODUCT may make a
one-time permanent transfer of this EULA and SOFTWARE PRODUCT only directly to
an end user. This transfer must include all of the SOFTWARE PRODUCT (including
all component parts, the media and printed materials, any upgrades, this EULA,
and, if applicable, the Certificate of Authenticity). Such transfer may not be
by way of consignment or any other indirect transfer. The transferee of such
one-time transfer must agree to comply with the terms of this EULA, including
the obligation not to further transfer this EULA and SOFTWARE PRODUCT.
5.7	Separation of Components. The SOFTWARE PRODUCT is licensed as a single
product. Its component parts may not be separated for use by more than one user. 
5.8	Termination. Without prejudice to any other rights, Microsoft may terminate
this EULA if you fail to comply with the terms and conditions of this EULA. In
such event, you must destroy all copies of the SOFTWARE PRODUCT and all of its
component parts.

6.	PRERELEASE CODE. Portions of the SOFTWARE PRODUCT may be identified as
prerelease code ("Prerelease Code"). Such Prerelease Code is not at the level of
performance and compatibility of the final, generally available product
offering. The Prerelease Code may not operate correctly and may be substantially
modified prior to first commercial shipment. Microsoft is not obligated to make
this or any later version of the Prerelease Code commercially available. The
grant of license to use Prerelease Code expires upon availability of a
commercial release of the Prerelease Code from Microsoft. NOTE: In the event
that Prerelease Code contains a separate end-user license agreement, the terms
and conditions of such end-user license agreement shall govern your use of the
corresponding Prerelease Code.

7.	UPGRADES. If the SOFTWARE PRODUCT is labeled as an upgrade, you must be
properly licensed to use a product identified by Microsoft as being eligible for
the upgrade in order to use the SOFTWARE PRODUCT. A SOFTWARE PRODUCT labeled as
an upgrade replaces and/or supplements the product that formed the basis for
your eligibility for the upgrade. You may use the resulting upgraded product
only in accordance with the terms of this EULA. If the SOFTWARE PRODUCT is an
upgrade of a component of a package of software programs that you licensed as a
single product, the SOFTWARE PRODUCT may be used and transferred only as part of
that single product package and may not be separated for use on more than one
computer.

8.	COPYRIGHT. All title and intellectual property rights in and to the SOFTWARE
PRODUCT (including but not limited to any images, photographs, animations,
video, audio, music, text, and "applets" incorporated into the SOFTWARE
PRODUCT), the accompanying printed materials, and any copies of the SOFTWARE
PRODUCT are owned by Microsoft or its suppliers. All title and intellectual
property rights in and to the content which may be accessed through use of the
SOFTWARE PRODUCT is the property of the respective content owner and may be
protected by applicable copyright or other intellectual property laws and
treaties. This EULA grants you no rights to use such content. All rights not
expressly granted are reserved by Microsoft.

9.	U.S. GOVERNMENT RESTRICTED RIGHTS. The SOFTWARE PRODUCT and documentation are
provided with RESTRICTED RIGHTS. Use, duplication, or disclosure by the
Government is subject to restrictions as set forth in subparagraph (c)(1)(ii) of
the Rights in Technical Data and Computer Software clause at DFARS 252.227-7013
or subparagraphs (c)(1) and (2) of the Commercial Computer Software-Restricted
Rights at 58 CFR 52.227-19, as applicable. Manufacturer is Microsoft
Corporation/One Microsoft Way/Redmond, WA 98052-6399.

10.	EXPORT RESTRICTIONS. You agree that you will not export or re-export the
SOFTWARE PRODUCT, any part thereof, or any process or service that is the direct
product of the SOFTWARE PRODUCT (the foregoing collectively referred to as the
"Restricted Components"), to any country, person, entity or end user subject to
U.S. export restrictions. You specifically agree not to export or re-export any
of the Restricted Components (i) to any country to which the U.S. has embargoed
or restricted the export of goods or services, which currently include, but are
not necessarily limited to Cuba, Iran, Iraq, Libya, North Korea, Sudan and
Syria, or to any national of any such country, wherever located, who intends to
transmit or transport the Restricted Components back to such country; (ii) to
any end-user who you know or have reason to know will utilize the Restricted
Components in the design, development or production of nuclear, chemical or
biological weapons; or (iii) to any end-user who has been prohibited from
participating in U.S. export transactions by any federal agency of the U.S.
government. You warrant and represent that neither the BXA nor any other U.S.
federal agency has suspended, revoked, or denied your export privileges.

11.	NOTE ON JAVA SUPPORT. THE SOFTWARE PRODUCT CONTAINS SUPPORT FOR PROGRAMS
WRITTEN IN JAVA. JAVA TECHNOLOGY IS NOT FAULT TOLERANT AND IS NOT DESIGNED,
MANUFACTURED, OR INTENDED FOR USE OR RESALE AS ONLINE CONTROL EQUIPMENT IN
HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE PERFORMANCE, SUCH AS IN THE OPERATION
OF NUCLEAR FACILITIES, AIRCRAFT NAVIGATION OR COMMUNICATIONS SYSTEMS, AIR
TRAFFIC CONTROL, DIRECT LIFE SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE
FAILURE OF JAVA TECHNOLOGY COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR
SEVERE PHYSICAL OR ENVIRONMENTAL DAMAGE.
MISCELLANEOUS
If you acquired this product in the United States, this EULA is governed by the
laws of the State of Washington. 
If you acquired this product in Canada, this EULA is governed by the laws of the
Province of Ontario, Canada. Each of the parties hereto irrevocably attorns to
the jurisdiction of the courts of the Province of Ontario and further agrees to
commence any litigation which may arise hereunder in the courts located in the
Judicial District of York, Province of Ontario. 
If this product was acquired outside the United States, then local law may apply.
Should you have any questions concerning this EULA, or if you desire to contact
Microsoft for any reason, please contact Microsoft, or write: Microsoft Sales
Information Center/One Microsoft Way/Redmond, WA 98052-6399. 

LIMITED WARRANTY
LIMITED WARRANTY. Except with respect to the REDISTRIBUTABLES, which are
provided "as is," without warranty of any kind,  Microsoft warrants that (a) the
SOFTWARE PRODUCT will perform substantially in accordance with the accompanying
written materials for a period of ninety (90) days from the date of receipt, and
(b) any Support Services provided by Microsoft shall be substantially as
described in applicable written materials provided to you by Microsoft, and
Microsoft support engineers will make commercially reasonable efforts to solve
any problem. To the extent allowed by applicable law, implied warranties on the
SOFTWARE PRODUCT, if any, are limited to ninety (90) days. Some
states/jurisdictions do not allow limitations on duration of an implied
warranty, so the above limitation may not apply to you.

CUSTOMER REMEDIES. Microsoft's and its suppliers' entire liability and your
exclusive remedy shall be, at Microsoft's option, either (a) return of the price
paid, if any, or (b) repair or replacement of the SOFTWARE PRODUCT that does not
meet Microsoft's Limited Warranty and that is returned to Microsoft with a copy
of your receipt. This Limited Warranty is void if failure of the SOFTWARE
PRODUCT has resulted from accident, abuse, or misapplication. Any replacement
SOFTWARE PRODUCT will be warranted for the remainder of the original warranty
period or thirty (30) days, whichever is longer. Outside the United States,
neither these remedies nor any product support services offered by Microsoft are
available without proof of purchase from an authorized international source.

NO OTHER WARRANTIES. TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW,
MICROSOFT AND ITS SUPPLIERS DISCLAIM ALL OTHER WARRANTIES AND CONDITIONS, EITHER
EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, IMPLIED WARRANTIES OR
CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, TITLE AND
NON-INFRINGEMENT, WITH REGARD TO THE SOFTWARE PRODUCT, AND THE PROVISION OF OR
FAILURE TO PROVIDE SUPPORT SERVICES. THIS LIMITED WARRANTY GIVES YOU SPECIFIC
LEGAL RIGHTS. YOU MAY HAVE OTHERS, WHICH VARY FROM STATE/JURISDICTION TO
STATE/JURISDICTION.

LIMITATION OF LIABILITY. TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW, IN
NO EVENT SHALL MICROSOFT OR ITS SUPPLIERS BE LIABLE FOR ANY SPECIAL, INCIDENTAL,
INDIRECT, OR CONSEQUENTIAL DAMAGES WHATSOEVER (INCLUDING, WITHOUT LIMITATION,
DAMAGES FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, LOSS OF BUSINESS
INFORMATION, OR ANY OTHER PECUNIARY LOSS) ARISING OUT OF THE USE OF OR INABILITY
TO USE THE SOFTWARE PRODUCT OR THE FAILURE TO PROVIDE SUPPORT SERVICES, EVEN IF
MICROSOFT HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. IN ANY CASE,
MICROSOFT'S ENTIRE LIABILITY UNDER ANY PROVISION OF THIS EULA SHALL BE LIMITED
TO THE GREATER OF THE AMOUNT ACTUALLY PAID BY YOU FOR THE SOFTWARE PRODUCT OR
U.S.$5.00; PROVIDED, HOWEVER, IF YOU HAVE ENTERED INTO A MICROSOFT SUPPORT
SERVICES AGREEMENT, MICROSOFT'S ENTIRE LIABILITY REGARDING SUPPORT SERVICES
SHALL BE GOVERNED BY THE TERMS OF THAT AGREEMENT. BECAUSE SOME
STATES/JURISDICTIONS DO NOT ALLOW THE EXCLUSION OR LIMITATION OF LIABILITY, THE
ABOVE LIMITATION MAY NOT APPLY TO YOU.


Si vous avez acquis votre produit Microsoft au CANADA, la garantie
limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe
suivante vous concerne :

GARANTIE LIMITEE
GARANTIE LIMITýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýE - Sauf pour celles du
REDISTRIBUTABLES, qui sont
fournies
"comme telles," sans acune garantie quelle qu'elle soit,  Microsoft garantit que
(a) la performance du LOGICIEL sera substantiellement en
conformitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý avec la
documentation qui accompagne le LOGICIEL, pour une
pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriode de
quatre-vingt-dix
(90) jours ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý compter de la date de
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýception; et
(b) tout support
technique
fourni par Microsoft sera substantiellement en
conformitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý avec toute
documentation affýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýrente fournie par Microsoft et
que les membres
du support
technique de Microsoft feront des efforts raisonnables pour
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsoudre toute
difficultýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý technique
dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýcoulant de l'utilisation du
LOGICIEL. Certaines
juridictions ne permettent pas de limiter dans le temps l'application de la
prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente garantie. Aussi, la limite
stipulýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe
ci-haut pourrait ne pas
s'appliquer
dans votre cas. Dans la mesure permise par la loi, toute garantie implicite
portant sur le LOGICIEL, le cas
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýchýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýant, est
limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý une pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriode de
quatre-vingt-dix (90) jours.

RECOURS DU CLIENT - La seule obligation de Microsoft et de ses fournisseurs et
votre recours exclusif seront, au choix de Microsoft, soit (a) le remboursement
du prix payýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý, si applicable, ou (b) la
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýparation
ou le
remplacement du
LOGICIEL
qui n'est pas conforme ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý la Garantie
Limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe de
Microsoft et qui est
retournýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý
Microsoft avec une copie de votre reýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýu. Cette
Garantie
Limitýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe est
nulle si le
dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýfaut du LOGICIEL est
causýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý par un accident, un
traitement abusif
ou une
mauvaise application. Tout LOGICIEL de remplacement sera garanti pour le reste
de la pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriode de garantie initiale ou pour trente
(30) jours,
selon la plus
longue de ces pýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýriodes. A
l'extýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýrieur des
Etats-Unis, aucun de ces
recours non
plus que le support technique offert par Microsoft ne sont disponibles sans une
preuve d'achat provenant d'une source authorisýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýe. 

AUCUNE AUTRE GARANTIE - DANS LA MESURE PREVUE PAR LA LOI, MICROSOFT ET SES
FOURNISSEURS EXCLUENT TOUTE AUTRE GARANTIE OU CONDITION, EXPRESSE OU IMPLICITE,
Y COMPRIS MAIS NE SE LIMITANT PAS AUX GARANTIES OU CONDITIONS IMPLICITES DU
CARACTERE ADEQUAT POUR LA COMMERCIALISATION OU UN USAGE PARTICULIER EN CE QUI
CONCERNE LE LOGICIEL OU CONCERNANT LE TITRE, L'ABSENCE DE
CONTREFAýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýON DUDIT
LOGICIEL, ET TOUTE DOCUMENTATION ECRITE QUI L'ACCOMPAGNE, AINSI QUE POUR TOUTE
DISPOSITION CONCERNANT LE SUPORT TECHNIQUE OU LA
FAýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýON DONT
CELUI-CI A ETE
RENDU. CETTE GARANTIE LIMITEE VOUS ACCORDE DES DROITS JURIDIQUES SPECIFIQUES. 

PAS DE RESPONSABILITE POUR LES DOMMAGES INDIRECTS - MICROSOFT OU SES
FOURNISSEURS NE SERONT PAS RESPONSABLES, EN AUCUNE CIRCONSTANCE, POUR TOUT
DOMMAGE SPECIAL, INCIDENT, INDIRECT, OU CONSEQUENT QUEL QU'IL SOIT (Y COMPRIS,
SANS LIMITATION, LES DOMMAGES ENTRAINES PAR LA PERTE DE BENEFICES,
L'INTERRUPTION DES ACTIVITES, LA PERTE D'INFORMATION OU TOUTE AUTRE PERTE
PECUNIAIRE) DECOULANT DE OU RELIE A LA LICENCE D'ACCES DU CLIENTET CE, MEME SI
MICROSOFT A ETE AVISEE DE LA POSSIBILITE DE TELS DOMMAGES. LA RESPONSABILITE DE
MICROSOFT EN VERTU DE TOUTE DISPOSITION DE CETTE CONVENTION NE POURRA EN AUCUN
TEMPS EXCEDER LE PLUS ELEVE ENTRE I) LE MONTANT EFFECTIVEMENT PAYE PAR VOUS POUR
LA LICENCE D'ACCES DU CLIENT OU II) U.S.$5.00. ADVENANT QUE VOUS AYEZ CONTRACTE
PAR ENTENTE DISTINCTE AVEC MICROSOFT POUR UN SUPPORT TECHNIQUE ETENDU, VOUS
SEREZ LIE PAR LES TERMES D' UNE TELLE ENTENTE.

La prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente Convention est
rýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýgie par les lois en
vigeur dans ela
province
d'Ontario, Canada. Chacune des parties ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý la
prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente reconnaýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýt
irrýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýývocablement
la compýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýtence des tribunaux de la province
d'Ontario et consent
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý
instituer tout
litige qui pourrait dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýcouler de la
prýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsente
auprýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýs des
tribunaux
situýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýs dans le
district judiciaire de York, province d'Ontario.

Au cas oýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý vous auriez des questions concernant
cette licence ou
que vous
dýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýsiriez vous mettre en rapport avec Microsoft
pour quelque
raison que ce
soit,
veuillez contacter la succursale Microsoft desservant votre pays, dont l'adresse
est fournie dans ce produit, ou ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýcrire
ýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýýý:
Microsoft Sales
Information Center, 
One Microsoft Way, Redmond, Washington 98052-6399.

***************************************************************************

%%The following software may be included in this product:
zlib

Use of any of this software is governed by the terms of the license below:

zlib.h -- interface of the 'zlib' general purpose compression library
  version 1.1.3, July 9th, 1998

  Copyright (C) 1995-1998 Jean-loup Gailly and Mark Adler

  This software is provided 'as-is', without any express or implied
  warranty.  In no event will the authors be held liable for any damages
  arising from the use of this software.

  Permission is granted to anyone to use this software for any purpose,
  including commercial applications, and to alter it and redistribute it
  freely, subject to the following restrictions:

  1. The origin of this software must not be misrepresented; you must not
     claim that you wrote the original software. If you use this software
     in a product, an acknowledgment in the product documentation would be
     appreciated but is not required.
  2. Altered source versions must be plainly marked as such, and must not be
     misrepresented as being the original software.
  3. This notice may not be removed or altered from any source distribution.

  Jean-loup Gailly        Mark Adler
  jloup@gzip.org          madler@alumni.caltech.edu


  The data format used by the zlib library is described by RFCs (Request for
  Comments) 1950 to 1952 in the files ftp://ds.internic.net/rfc/rfc1950.txt
  (zlib format), rfc1951.txt (deflate format) and rfc1952.txt (gzip format

Additional License(s)

Copyright notice:

 (C) 1995-2002 Jean-loup Gailly and Mark Adler

  This software is provided 'as-is', without any express or implied
  warranty.  In no event will the authors be held liable for any damages
  arising from the use of this software.

  Permission is granted to anyone to use this software for any purpose,
  including commercial applications, and to alter it and redistribute it
  freely, subject to the following restrictions:

  1. The origin of this software must not be misrepresented; you must not
     claim that you wrote the original software. If you use this software
     in a product, an acknowledgment in the product documentation would be
     appreciated but is not required.
  2. Altered source versions must be plainly marked as such, and must not be
     misrepresented as being the original software.
  3. This notice may not be removed or altered from any source distribution.

  Jean-loup Gailly        Mark Adler
  jloup@gzip.org          madler@alumni.caltech.edu

If you use the zlib library in a product, we would appreciate *not*
receiving lengthy legal documents to sign. The sources are provided
for free but without warranty of any kind.  The library has been
entirely written by Jean-loup Gailly and Mark Adler; it does not
include third-party code.

If you redistribute modified sources, we would appreciate that you include
in the file ChangeLog history information documenting your changes.

***************************************************************************

%%The following software may be included in this product:
DES and 3DES

Use of any of this software is governed by the terms of the license below:

ACME Labs Freeware License

All the free software available on the ACME Labs web site has a copyright notice
like this one:

Copyright ýýýýýýýýýýýýýýýý 2000 by Jef Poskanzer .  All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

So what does this legalese mean? This is a modified version of the BSD license.
You may be more familiar with the Gnu Public License, since it gets a lot of
press. Well, forget about that. The BSD license is very different. While the Gnu
license puts all sorts of restrictions on what you can do with the software,
BSD-style licenses say "Hey, do what you like, we don't care. Just let people
know we wrote it, and don't sue us." That's really all there is to it. The first
paragraph with the numbered items says you can do what you like with the code,
as long as you keep our name on it. The original BSD license has a couple more
provisions there, we got rid of those. The second paragraph, all in capital
letters, is a standard legal boilerplate notification that tries to make it
difficult for anyone to successfully sue us over the software.

By the way, FreshMeat has a nice list of different types of freeware licenses.
There are quite a few besides BSD and GPL. There's also the OpenSource.org list
of open source licenses, which is not quite the same as the freeware licenses.

Here are some questions people ask about the license:

"Can I use your software in a freeware product?"
    Sure. 
"Can I use your software in a commercial product? Do I have to pay you?"
    Yes you can, and no you don't have to, although we certainly won't object to
any checks you care to send our way. 
"If I use your software as part of my product, do I have to include your source
code? Or my source code?""
    No. You're thinking of the Gnu license. Our license just says that if you
distribute our source code then you must leave in the license text; but you are
free to use it in binary-only form if you like, and put the license text in your
documentation instead. 
"Can I make my own version of your software?"
    Of course. 
"Can I distribute my own version of your software?"
    Yes, although we'd prefer that you didn't. Having multiple versions of the
same thing floating around is inherently distasteful. If you absolutely must
distribute your own modified version of our software, please name it something
else to avoid confusing people. 
"Would you please send me a signed disclaimer, so my company's lawyers are happy?"
    No. The license in the source code is sufficient. 
"We don't want to put your license in our documentation or distribute your
source, can we use your software anyway?"
    We occasionally grant special permission for this sort of thing. A donation
to ACME Labs will probably improve the chances. 


Additional License(s)

Copyright ýýýý 2000 by Jef Poskanzer .  All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

***************************************************************************

%%The following software may be included in this product:
Cryptix

Use of any of this software is governed by the terms of the license below:

Cryptix General License

Copyright } 1995-2001 The Cryptix Foundation Limited. All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

1.Redistributions of source code must retain the copyright notice, this list of
conditions and the following disclaimer. 
2.Redistributions in binary form must reproduce the above copyright notice, this
list of  conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution. 

THIS SOFTWARE IS PROVIDED BY THE CRYPTIX FOUNDATION LIMITED AND
CONTRIBUTORS ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE CRYPTIX
FOUNDATION LIMITED OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
                                                                               
                     
  

Additional License(s)

Our group does not have access to the source file.  Please check with the J2SE
security group.

***************************************************************************

%%The following software may be included in this product:
SSL

Use of any of this software is governed by the terms of the license below:

OpenSSL License
LICENSE ISSUES
The OpenSSL toolkit stays under a dual license, i.e. both the conditions of the
OpenSSL License and the original SSLeay license apply to the toolkit. See below
for the actual license texts. Actually both licenses are BSD-style Open Source
licenses. In case of any license issues related to OpenSSL please contact
openssl-core@openssl.org.
Copyright (c) 1998-2002 The OpenSSL Project.  All rights reserved.
Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:
1.Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.
2.Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.
3.All advertising materials mentioning features or use of this software must
display the following acknowledgment:
"This product includes software developed by the OpenSSL Project for use in the
OpenSSL Toolkit. (http://www.openssl.org/)"
4.The names "OpenSSL Toolkit" and "OpenSSL Project" must not be used to endorse
or promote products derived from this software without prior written permission.
For written permission, please contact openssl-core@openssl.org.
5.Products derived from this software may not be called "OpenSSL” nor may
"OpenSSL" appear in their names without prior written permission of the OpenSSL
Project.
6.Redistributions of any form whatsoever must retain the following acknowledgment:
"This product includes software developed by the OpenSSL Project for use in the
OpenSSL Toolkit (http://www.openssl.org/)"
THIS SOFTWARE IS PROVIDED BY THE OpenSSL PROJECT ``AS IS'' AND ANY EXPRESSED OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO
EVENT SHALL THE OpenSSL PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
This product includes cryptographic software written by Eric Young
(eay@cryptsoft.com).  This product includes software written by Tim Hudson
(tjh@cryptsoft.com).


Original SSLeay License
Copyright (C) 1995-1998 Eric Young (eay@cryptsoft.com)
All rights reserved.
This package is an SSL implementation written by Eric Young (eay@cryptsoft.com). 
The implementation was written so as to conform with Netscapes SSL.
This library is free for commercial and non-commercial use as long as the
following conditions are aheared to.  The following conditions apply to all code
found in this distribution, be it the RC4, RSA, lhash, DES, etc., code; not just
the SSL code.  The SSL documentation included with this distribution is covered
by the same copyright terms except that the holder is Tim Hudson
(tjh@cryptsoft.com).
Copyright remains Eric Young's, and as such any Copyright notices in the code
are not to be removed. If this package is used in a product, Eric Young should
be given attribution as the author of the parts of the library used. This can be
in the form of a textual message at program startup or in documentation (online
or textual) provided with the package.
Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:
1.Redistributions of source code must retain the copyright notice, this list of
conditions and the following disclaimer.
2.Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.
3.All advertising materials mentioning features or use of this software must
display the following acknowledgement:
"This product includes cryptographic software written by Eric Young
(eay@cryptsoft.com)"
The word 'cryptographic' can be left out if the rouines from the library being
used are not cryptographic related :-).
4.If you include any Windows specific code (or a derivative thereof) from the
apps directory (application code) you must include an acknowledgement:
"This product includes software written by Tim Hudson (tjh@cryptsoft.com)"
THIS SOFTWARE IS PROVIDED BY ERIC YOUNG ``AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO
EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
The license and distribution terms for any publically available version or
derivative of this code cannot be changed.  i.e. this code cannot simply be
copied and put under another distribution license [including the GNU Public
License.]

Additional License(s)

RSA Data Security, Inc. Message Digest License
Copyright (C) 1991-2, RSA Data Security, Inc. Created 1991.
All rights reserved.
License to copy and use this software is granted provided that it is
identified as the "RSA Data Security, Inc. MD4 Message-Digest
Algorithm" in all material mentioning or referencing this software or
this function.
License is also granted to make and use derivative works provided that
such works are identified as "derived from the RSA Data Security, Inc.
MD4 Message-Digest Algorithm" in all material mentioning or
referencing the derived work.
RSA Data Security, Inc. makes no representations concerning either the
merchantability of this software or the suitability of this software
for any particular purpose. It is provided "as is" without express or
implied warranty of any kind.
These notices must be retained in any copies of any part of this
documentation and/or software.

MD5 License 
"THE BEER-WARE LICENSE" (Revision 42):
 wrote this file.  As long as you retain this
notice you can do whatever you want with this stuff. If we meet some
day, and you think this stuff is worth it, you can buy me a beer in
return.   Poul-Henning Kamp

***************************************************************************

%%The following software may be included in this product:
MinML2ME

Use of any of this software is governed by the terms of the license below:

Copyright (c) 2000, 2001 John Wilson (tug@wilson.co.uk).
All rights reserved.
Redistribution and use in source and binary forms,
with or without modification, are permitted provided
that the following conditions are met:

Redistributions of source code must retain the above
copyright notice, this list of conditions and the
following disclaimer.

Redistributions in binary form must reproduce the
above copyright notice, this list of conditions and
the following disclaimer in the documentation and/or
other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY JOHN WILSON ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JOHN WILSON
BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
OF THE POSSIBILITY OF SUCH DAMAGE

Additional License(s)

The code is 'copyright'ed to John Wilson. He'd probably be willing to give us
the copyright given certain conditions (like leaving his name in it).

***************************************************************************

%%The following software may be included in this product:
SAX

Use of any of this software is governed by the terms of the license below:

There is not a license - it is in the public domain

Copyright Disclaimers 

This page includes statements to that effect by David Megginson, who would have
been able to claim copyright for the original work. 

SAX 1.0 

Version 1.0 of the Simple API for XML (SAX), created collectively by the
membership of the XML-DEV mailing list, is hereby released into the public
domain.

No one owns SAX: you may use it freely in both commercial and non-commercial
applications, bundle it with your software distribution, include it on a CD-ROM,
list the source code in a book, mirror the documentation at your own web site,
or use it in any other way you see fit.

SAX 2.0 

I hereby abandon any property rights to SAX 2.0 (the Simple API for XML), and
release all of the SAX 2.0 source code, compiled code, and documentation
contained in this distribution into the Public Domain. SAX comes with NO
WARRANTY or guarantee of fitness for any purpose.

                           David Megginson, david@megginson.com
                           2000-05-05

Additional License(s)

Public domain software

***************************************************************************

%%The following software may be included in this product:
Document Object Model (DOM)

Use of any of this software is governed by the terms of the license below:

W3C} SOFTWARE NOTICE AND LICENSE

Copyright } 1994-2002 World Wide Web Consortium, (Massachusetts Institute of
Technology, Institut National de
Recherche en Informatique et en Automatique, Keio University). All Rights
Reserved.
http://www.w3.org/Consortium/Legal/

This W3C work (including software, documents, or other related items) is being
provided by the copyright holders under the following
license. By obtaining, using and/or copying this work, you (the licensee) agree
that you have read, understood, and will comply with the
following terms and conditions:

Permission to use, copy, modify, and distribute this software and its
documentation, with or without modification,  for any purpose and
without fee or royalty is hereby granted, provided that you include the
following on ALL copies of the software and documentation or
portions thereof, including modifications, that you make:

    1.The full text of this NOTICE in a location viewable to users of the
redistributed or derivative work. 
    2.Any pre-existing intellectual property disclaimers, notices, or terms and
conditions. If none exist, a short notice of the following form
      (hypertext is preferred, text is permitted) should be used within the body
of any redistributed or derivative code: "Copyright }
      [$date-of-software] World Wide Web Consortium, (Massachusetts Institute of
Technology, Institut National de Recherche en
      Informatique et en Automatique, Keio University). All Rights Reserved.
http://www.w3.org/Consortium/Legal/" 
    3.Notice of any changes or modifications to the W3C files, including the
date changes were made. (We recommend you provide URIs to
      the location from which the code is derived.) 

THIS SOFTWARE AND DOCUMENTATION IS PROVIDED "AS IS," AND COPYRIGHT HOLDERS MAKE
NO REPRESENTATIONS OR
WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO, WARRANTIES OF
MERCHANTABILITY OR FITNESS FOR ANY
PARTICULAR PURPOSE OR THAT THE USE OF THE SOFTWARE OR DOCUMENTATION WILL NOT
INFRINGE ANY THIRD PARTY
PATENTS, COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS.

COPYRIGHT HOLDERS WILL NOT BE LIABLE FOR ANY DIRECT, INDIRECT, SPECIAL OR
CONSEQUENTIAL DAMAGES ARISING OUT
OF ANY USE OF THE SOFTWARE OR DOCUMENTATION.

The name and trademarks of copyright holders may NOT be used in advertising or
publicity pertaining to the software without specific,
written prior permission. Title to copyright in this software and any associated
documentation will at all times remain with copyright holders.

Additional License(s)

MIT, BSD type compatible

***************************************************************************

%%The following software may be included in this product:
Document Object Model (DOM)

Use of any of this software is governed by the terms of the license below:

W3Cýý SOFTWARE NOTICE AND LICENSE

http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231

This work (and included software, documentation such as READMEs, or other 
related items) is being
provided by the copyright holders under the following license. By obtaining, 
using and/or copying this work, you
(the licensee) agree that you have read, understood, and will comply with the 
following terms and conditions.

Permission to copy, modify, and distribute this software and its documentation, 
with or without modification, for
any purpose and without fee or royalty is hereby granted, provided that you 
include the following on ALL copies
of the software and documentation or portions thereof, including modifications:

   1.The full text of this NOTICE in a location viewable to users of the 
redistributed or derivative work. 
   2.Any pre-existing intellectual property disclaimers, notices, or terms and 
conditions. If none exist, the
     W3C Software Short Notice should be included (hypertext is preferred, text 
is permitted) within the body
     of any redistributed or derivative code. 
   3.Notice of any changes or modifications to the files, including the date 
changes were made. (We
     recommend you provide URIs to the location from which the code is derived.) 

THIS SOFTWARE AND DOCUMENTATION IS PROVIDED "AS IS," AND COPYRIGHT HOLDERS MAKE
NO REPRESENTATIONS OR WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
TO,
WARRANTIES OF MERCHANTABILITY OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT THE
USE OF THE SOFTWARE OR DOCUMENTATION WILL NOT INFRINGE ANY THIRD PARTY PATENTS,
COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS.

COPYRIGHT HOLDERS WILL NOT BE LIABLE FOR ANY DIRECT, INDIRECT, SPECIAL OR
CONSEQUENTIAL DAMAGES ARISING OUT OF ANY USE OF THE SOFTWARE OR DOCUMENTATION.

The name and trademarks of copyright holders may NOT be used in advertising or 
publicity pertaining to the
software without specific, written prior permission. Title to copyright in this 
software and any associated
documentation will at all times remain with copyright holders.

____________________________________

This formulation of W3C's notice and license became active on December 31 2002. 
This version removes the
copyright ownership notice such that this license can be used with materials 
other than those owned by the
W3C, reflects that ERCIM is now a host of the W3C, includes references to this 
specific dated version of the
license, and removes the ambiguous grant of "use". Otherwise, this version is 
the same as the previous
version and is written so as to preserve the Free Software Foundation's 
assessment of GPL compatibility and
OSI's certification under the Open Source Definition. Please see our Copyright 
FAQ for common questions
about using materials from our site, including specific terms and conditions for 
packages like libwww, Amaya,
and Jigsaw. Other questions about this notice can be directed to 
site-policy@w3.org.
 

Joseph Reagle  

Last revised by Reagle $Date: 2003/01/16 15:01:10 $

Additional License(s)

 

  Copyright (c) 2003 World Wide Web Consortium,
 
  (Massachusetts Institute of Technology, European Research Consortium for
  Informatics and Mathematics, Keio University). All Rights Reserved. This
  work is distributed under the W3C(r) Software License [1] in the hope that
  it will be useful, but WITHOUT ANY WARRANTY; without even the implied
  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 
  [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 

***************************************************************************

%%The following software may be included in this product:
Open SSL

Use of any of this software is governed by the terms of the license below:

LICENSE ISSUES
  ==============

  The OpenSSL toolkit stays under a dual license, i.e. both the conditions of
  the OpenSSL License and the original SSLeay license apply to the toolkit.
  See below for the actual license texts. Actually both licenses are BSD-style
  Open Source licenses. In case of any license issues related to OpenSSL
  please contact openssl-core@openssl.org.

  OpenSSL License
  ---------------

/* ====================================================================
 * Copyright (c) 1998-2005 The OpenSSL Project.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. All advertising materials mentioning features or use of this
 *    software must display the following acknowledgment:
 *    "This product includes software developed by the OpenSSL Project
 *    for use in the OpenSSL Toolkit. (http://www.openssl.org/)"
 *
 * 4. The names "OpenSSL Toolkit" and "OpenSSL Project" must not be used to
 *    endorse or promote products derived from this software without
 *    prior written permission. For written permission, please contact
 *    openssl-core@openssl.org.
 *
 * 5. Products derived from this software may not be called "OpenSSL"
 *    nor may "OpenSSL" appear in their names without prior written
 *    permission of the OpenSSL Project.
 *
 * 6. Redistributions of any form whatsoever must retain the following
 *    acknowledgment:
 *    "This product includes software developed by the OpenSSL Project
 *    for use in the OpenSSL Toolkit (http://www.openssl.org/)"
 *
 * THIS SOFTWARE IS PROVIDED BY THE OpenSSL PROJECT ``AS IS'' AND ANY
 * EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE OpenSSL PROJECT OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 *
 * This product includes cryptographic software written by Eric Young
 * (eay@cryptsoft.com).  This product includes software written by Tim
 * Hudson (tjh@cryptsoft.com).
 *
 */

 Original SSLeay License
 -----------------------

/* Copyright (C) 1995-1998 Eric Young (eay@cryptsoft.com)
 * All rights reserved.
 *
 * This package is an SSL implementation written
 * by Eric Young (eay@cryptsoft.com).
 * The implementation was written so as to conform with Netscapes SSL.
 *
 * This library is free for commercial and non-commercial use as long as
 * the following conditions are aheared to.  The following conditions
 * apply to all code found in this distribution, be it the RC4, RSA,
 * lhash, DES, etc., code; not just the SSL code.  The SSL documentation
 * included with this distribution is covered by the same copyright terms
 * except that the holder is Tim Hudson (tjh@cryptsoft.com).
 *
 * Copyright remains Eric Young's, and as such any Copyright notices in
 * the code are not to be removed.
 * If this package is used in a product, Eric Young should be given attribution
 * as the author of the parts of the library used.
 * This can be in the form of a textual message at program startup or
 * in documentation (online or textual) provided with the package.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    "This product includes cryptographic software written by
 *     Eric Young (eay@cryptsoft.com)"
 *    The word 'cryptographic' can be left out if the rouines from the library
 *    being used are not cryptographic related :-).
 * 4. If you include any Windows specific code (or a derivative thereof) from
 *    the apps directory (application code) you must include an 
acknowledgement:
 *    "This product includes software written by Tim Hudson 
(tjh@cryptsoft.com)"
 *
 * THIS SOFTWARE IS PROVIDED BY ERIC YOUNG ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * The licence and distribution terms for any publically available version or
 * derivative of this code cannot be changed.  i.e. this code cannot simply be
 * copied and put under another distribution licence
 * [including the GNU Public Licence.]
 */

***************************************************************************

%%The following software may be included in this product:
Apache Batik

Use of any of this software is governed by the terms of the license below:

 ============================================================================
                   The Apache Software License, Version 1.1
 ============================================================================
 
 Copyright (C) 2000 The Apache Software Foundation. All rights reserved.
 
 Redistribution and use in source and binary forms, with or without modifica-
 tion, are permitted provided that the following conditions are met:
 
 1. Redistributions of  source code must  retain the above copyright  notice,
    this list of conditions and the following disclaimer.
 
 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.
 
 3. The end-user documentation included with the redistribution, if any, must
    include  the following  acknowledgment:  "This product includes  software
    developed  by the  Apache Software Foundation  (http://www.apache.org/)."
    Alternately, this  acknowledgment may  appear in the software itself,  if
    and wherever such third-party acknowledgments normally appear.
 
 4. The names "Batik" and  "Apache Software Foundation"  must not be  used to
    endorse  or promote  products derived  from this  software without  prior
    written permission. For written permission, please contact
    apache@apache.org.
 
 5. Products  derived from this software may not  be called "Apache", nor may
    "Apache" appear  in their name,  without prior written permission  of the
    Apache Software Foundation.
 
 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
 APACHE SOFTWARE  FOUNDATION  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLU-
 DING, BUT NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
 OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
 ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
 (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 This software  consists of voluntary contributions made  by many individuals
 on  behalf  of the Apache Software  Foundation. For more  information on the
 Apache Software Foundation, please see .



Additional License(s)

There are some files that are part of W3C SAC.

Copyright (c) 1999 World Wide Web Consortium

/*
 * Copyright (c) 1999 World Wide Web Consortium
 * (Massachusetts Institute of Technology, Institut National de Recherche
 *  en Informatique et en Automatique, Keio University).
 * All Rights Reserved. http://www.w3.org/Consortium/Legal/
 *
 * The original version of this interface comes from SAX :
 * http://www.megginson.com/SAX/
 *

***************************************************************************

%%The following software may be included in this product:
Bitstream Vera Fonts

Use of any of this software is governed by the terms of the license below:

Copyright (c) 2003 by Bitstream, Inc. All Rights Reserved. Bitstream Vera is a
trademark of Bitstream, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy of
the fonts accompanying this license (ýýýFontsýýý) and associated documentation files
(the ýýýFont Softwareýýý), to reproduce and distribute the Font Software, including
without limitation the rights to use, copy, merge, publish, distribute, and/or
sell copies of the Font Software, and to permit persons to whom the Font
Software is furnished to do so, subject to the following conditions:

The above copyright and trademark notices and this permission notice shall be
included in all copies of one or more of the Font Software typefaces.

The Font Software may be modified, altered, or added to, and in particular the
designs of glyphs or characters in the Fonts may be modified and additional
glyphs or characters may be added to the Fonts, only if the fonts are renamed to
names not containing either the words ýýýBitstreamýýý or the word ýýýVeraýýý.

This License becomes null and void to the extent applicable to Fonts or Font
Software that has been modified and is distributed under the ýýýBitstream Veraýýý names.

The Font Software may be sold as part of a larger software package but no copy
of one or more of the Font Software typefaces may be sold by itself.

THE FONT SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO ANY WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT OF COPYRIGHT, PATENT, TRADEMARK, OR
OTHER RIGHT. IN NO EVENT SHALL BITSTREAM OR THE GNOME FOUNDATION BE LIABLE FOR
ANY CLAIM, DAMAGES OR OTHER LIABILITY, INCLUDING ANY GENERAL, SPECIAL, INDIRECT,
INCIDENTAL, OR CONSEQUENTIAL DAMAGES, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF THE USE OR INABILITY TO USE THE FONT SOFTWARE OR
FROM OTHER DEALINGS IN THE FONT SOFTWARE.

Except as contained in this notice, the names of Gnome, the Gnome Foundation,
and Bitstream Inc., shall not be used in advertising or otherwise to promote the
sale, use or other dealings in this Font Software without prior written
authorization from the Gnome Foundation or Bitstream Inc., respectively. For
further information, contact: fonts at gnome dot org.

Additional License(s)

Copyright statement and license notice as listed above.

***************************************************************************

%%The following software may be included in this product:
Apache Portable Runtime

Use of any of this software is governed by the terms of the license below:

Apache License, Version 2.0

Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/

TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION

1. Definitions.

"License" shall mean the terms and conditions for use, reproduction, and
distribution as defined by Sections 1 through 9 of this document.

"Licensor" shall mean the copyright owner or entity authorized by the copyright
owner that is granting the License.

"Legal Entity" shall mean the union of the acting entity and all other entities
that control, are controlled by, or are under common control with that entity.
For the purposes of this definition, "control" means (i) the power, direct or
indirect, to cause the direction or management of such entity, whether by
contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the
outstanding shares, or (iii) beneficial ownership of such entity.

"You" (or "Your") shall mean an individual or Legal Entity exercising
permissions granted by this License.

"Source" form shall mean the preferred form for making modifications, including
but not limited to software source code, documentation source, and configuration
files.

"Object" form shall mean any form resulting from mechanical transformation or
translation of a Source form, including but not limited to compiled object code,
generated documentation, and conversions to other media types.

"Work" shall mean the work of authorship, whether in Source or Object form, made
available under the License, as indicated by a copyright notice that is included
in or attached to the work (an example is provided in the Appendix below).

"Derivative Works" shall mean any work, whether in Source or Object form, that
is based on (or derived from) the Work and for which the editorial revisions,
annotations, elaborations, or other modifications represent, as a whole, an
original work of authorship. For the purposes of this License, Derivative Works
shall not include works that remain separable from, or merely link (or bind by
name) to the interfaces of, the Work and Derivative Works thereof.

"Contribution" shall mean any work of authorship, including the original version
of the Work and any modifications or additions to that Work or Derivative Works
thereof, that is intentionally submitted to Licensor for inclusion in the Work
by the copyright owner or by an individual or Legal Entity authorized to submit
on behalf of the copyright owner. For the purposes of this definition,
"submitted" means any form of electronic, verbal, or written communication sent
to the Licensor or its representatives, including but not limited to
communication on electronic mailing lists, source code control systems, and
issue tracking systems that are managed by, or on behalf of, the Licensor for
the purpose of discussing and improving the Work, but excluding communication
that is conspicuously marked or otherwise designated in writing by the copyright
owner as "Not a Contribution."

"Contributor" shall mean Licensor and any individual or Legal Entity on behalf
of whom a Contribution has been received by Licensor and subsequently
incorporated within the Work.

2. Grant of Copyright License. Subject to the terms and conditions of this
License, each Contributor hereby grants to You a perpetual, worldwide,
non-exclusive, no-charge, royalty-free, irrevocable copyright license to
reproduce, prepare Derivative Works of, publicly display, publicly perform,
sublicense, and distribute the Work and such Derivative Works in Source or
Object form.

3. Grant of Patent License. Subject to the terms and conditions of this License,
each Contributor hereby grants to You a perpetual, worldwide, non-exclusive,
no-charge, royalty-free, irrevocable (except as stated in this section) patent
license to make, have made, use, offer to sell, sell, import, and otherwise
transfer the Work, where such license applies only to those patent claims
licensable by such Contributor that are necessarily infringed by their
Contribution(s) alone or by combination of their Contribution(s) with the Work
to which such Contribution(s) was submitted. If You institute patent litigation
against any entity (including a cross-claim or counterclaim in a lawsuit)
alleging that the Work or a Contribution incorporated within the Work
constitutes direct or contributory patent infringement, then any patent licenses
granted to You under this License for that Work shall terminate as of the date
such litigation is filed.

4. Redistribution. You may reproduce and distribute copies of the Work or
Derivative Works thereof in any medium, with or without modifications, and in
Source or Object form, provided that You meet the following conditions:

   1. You must give any other recipients of the Work or Derivative Works a copy
of this License; and

   2. You must cause any modified files to carry prominent notices stating that
You changed the files; and

   3. You must retain, in the Source form of any Derivative Works that You
distribute, all copyright, patent, trademark, and attribution notices from the
Source form of the Work, excluding those notices that do not pertain to any part
of the Derivative Works; and

   4. If the Work includes a "NOTICE" text file as part of its distribution,
then any Derivative Works that You distribute must include a readable copy of
the attribution notices contained within such NOTICE file, excluding those
notices that do not pertain to any part of the Derivative Works, in at least one
of the following places: within a NOTICE text file distributed as part of the
Derivative Works; within the Source form or documentation, if provided along
with the Derivative Works; or, within a display generated by the Derivative
Works, if and wherever such third-party notices normally appear. The contents of
the NOTICE file are for informational purposes only and do not modify the
License. You may add Your own attribution notices within Derivative Works that
You distribute, alongside or as an addendum to the NOTICE text from the Work,
provided that such additional attribution notices cannot be construed as
modifying the License.

You may add Your own copyright statement to Your modifications and may provide
additional or different license terms and conditions for use, reproduction, or
distribution of Your modifications, or for any such Derivative Works as a whole,
provided Your use, reproduction, and distribution of the Work otherwise complies
with the conditions stated in this License.

5. Submission of Contributions. Unless You explicitly state otherwise, any
Contribution intentionally submitted for inclusion in the Work by You to the
Licensor shall be under the terms and conditions of this License, without any
additional terms or conditions. Notwithstanding the above, nothing herein shall
supersede or modify the terms of any separate license agreement you may have
executed with Licensor regarding such Contributions.

6. Trademarks. This License does not grant permission to use the trade names,
trademarks, service marks, or product names of the Licensor, except as required
for reasonable and customary use in describing the origin of the Work and
reproducing the content of the NOTICE file.

7. Disclaimer of Warranty. Unless required by applicable law or agreed to in
writing, Licensor provides the Work (and each Contributor provides its
Contributions) on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied, including, without limitation, any warranties
or conditions of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A
PARTICULAR PURPOSE. You are solely responsible for determining the
appropriateness of using or redistributing the Work and assume any risks
associated with Your exercise of permissions under this License.

8. Limitation of Liability. In no event and under no legal theory, whether in
tort (including negligence), contract, or otherwise, unless required by
applicable law (such as deliberate and grossly negligent acts) or agreed to in
writing, shall any Contributor be liable to You for damages, including any
direct, indirect, special, incidental, or consequential damages of any character
arising as a result of this License or out of the use or inability to use the
Work (including but not limited to damages for loss of goodwill, work stoppage,
computer failure or malfunction, or any and all other commercial damages or
losses), even if such Contributor has been advised of the possibility of such
damages.

9. Accepting Warranty or Additional Liability. While redistributing the Work or
Derivative Works thereof, You may choose to offer, and charge a fee for,
acceptance of support, warranty, indemnity, or other liability obligations
and/or rights consistent with this License. However, in accepting such
obligations, You may act only on Your own behalf and on Your sole
responsibility, not on behalf of any other Contributor, and only if You agree to
indemnify, defend, and hold each Contributor harmless for any liability incurred
by, or claims asserted against, such Contributor by reason of your accepting any
such warranty or additional liability.

*************************************************************************** 


END OF TERMS AND CONDITIONS

***************************************************************************




